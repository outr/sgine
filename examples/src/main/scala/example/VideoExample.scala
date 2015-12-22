package example

import java.nio.ByteBuffer

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.{GL20, Pixmap, Texture}
import com.sun.jna.Memory
import org.sgine._
import org.sgine.lwjgl.BasicDesktopApp
import org.sgine.screen.FPSLoggingSupport
import org.sgine.widget.Image
import uk.co.caprica.vlcj.player.MediaPlayerFactory
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
  private var buffer: ByteBuffer = _

  create.once {
    add(image)

    player.playMedia("trailer_1080p.ogg")
  }

  render.on {
    if (buffer != null) {
      val id = texture.getTextureObjectHandle
      Gdx.gl.glBindTexture(GL20.GL_TEXTURE_2D, id)
      Gdx.gl.glTexSubImage2D(GL20.GL_TEXTURE_2D, 0, 0, 0, width, height, GL20.GL_RGBA, GL20.GL_UNSIGNED_BYTE, buffer)
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
      buffer = nativeBuffers.head.getByteBuffer(0, width * height * 4)
    }
  }
}