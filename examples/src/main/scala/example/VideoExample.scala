package example

import java.nio.ByteBuffer
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.atomic.AtomicReference

import com.badlogic.gdx.graphics.{Pixmap, Texture}
import com.sun.jna.Memory
import org.sgine._
import org.sgine.lwjgl.BasicDesktopApp
import org.sgine.screen.FPSLoggingSupport
import org.sgine.video.Media
import org.sgine.widget.Image
import uk.co.caprica.vlcj.player._
import uk.co.caprica.vlcj.player.direct._

object VideoExample extends BasicDesktopApp with FPSLoggingSupport {
  val width = 1920
  val height = 1080

  lazy val texture = new Texture(width, height, Pixmap.Format.RGBA8888)
  lazy val image = new Image(texture) {
    position.center := ui.width / 2.0
    position.middle := ui.height / 2.0
    size.width := width.toDouble
    size.height := height.toDouble
  }
  lazy val factory = new MediaPlayerFactory("--no-video-title-show")
  lazy val player = factory.newDirectMediaPlayer(new TestBufferFormatCallback, new TestRenderCallback)
  private val queue = new ConcurrentLinkedQueue[PBO]
  private val readBuffer = new AtomicReference[PBO](null)

  create.once {
    queue.add(PBO(width, height))
    queue.add(PBO(width, height))
    add(image)

//    player.playMedia("trailer_1080p.ogg")
    println(s"Media: ${Media("trailer_1080p.ogg")}")
  }

  render.on {
    readBuffer.getAndSet(null) match {
      case null => // Nothing to read
      case pbo => {
        pbo.updateTexture(texture.getTextureObjectHandle)
        pbo.updateBuffer()
        queue.add(pbo)
      }
    }
  }

  dispose.on {
    player.release()
    factory.release()
  }

  class TestBufferFormatCallback extends BufferFormatCallback {
    override def getBufferFormat(sourceWidth: Int, sourceHeight: Int): BufferFormat = {
      new BufferFormat("RGBA", width, height, Array(width * 4), Array(height))
    }
  }

  class TestRenderCallback extends RenderCallback {
    override def display(mediaPlayer: DirectMediaPlayer, nativeBuffers: Array[Memory], bufferFormat: BufferFormat): Unit = {
      queue.poll() match {
        case null => // Nothing to do
        case pbo => {
          val videoBuffer = nativeBuffers.head.getByteBuffer(0, width * height * 4)
          pbo.buffer.put(videoBuffer)
          pbo.buffer.flip()
          val old = readBuffer.getAndSet(pbo)
          if (old != null) {
            queue.add(old)
          }
        }
      }
    }
  }
}

class PBO(id: Int, width: Int, height: Int) {
  import org.lwjgl.opengl.GL11._
  import org.lwjgl.opengl.GL15._
  import org.lwjgl.opengl.GL21._

  var buffer: ByteBuffer = _

  updateBuffer()

  def updateBuffer(): Unit = {
    glBindBuffer(GL_PIXEL_UNPACK_BUFFER, id)
    buffer = glMapBuffer(GL_PIXEL_UNPACK_BUFFER, GL_WRITE_ONLY, buffer)
    glBindBuffer(GL_PIXEL_UNPACK_BUFFER, 0)
  }

  def updateTexture(textureId: Int): Unit = {
    glBindBuffer(GL_PIXEL_UNPACK_BUFFER, id)
    glUnmapBuffer(GL_PIXEL_UNPACK_BUFFER)
    glBindTexture(GL_TEXTURE_2D, textureId)
    glTexSubImage2D(GL_TEXTURE_2D, 0, 0, 0, width, height, GL_RGBA, GL_UNSIGNED_BYTE, 0)
    glBindBuffer(GL_PIXEL_UNPACK_BUFFER, 0)
  }

  def dispose(): Unit = {
    glDeleteBuffers(id)
  }
}

object PBO {
  import org.lwjgl.opengl.GL15._
  import org.lwjgl.opengl.GL21._

  def apply(width: Int, height: Int): PBO = {
    val id = glGenBuffers()
    glBindBuffer(GL_PIXEL_UNPACK_BUFFER, id)
    glBufferData(GL_PIXEL_UNPACK_BUFFER, width * height * 4, GL_STREAM_DRAW)
    glBindBuffer(GL_PIXEL_UNPACK_BUFFER, 0)
    new PBO(id, width, height)
  }
}