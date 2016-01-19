package org.sgine.video

import java.nio.ByteBuffer
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.atomic.{AtomicInteger, AtomicReference}

import com.badlogic.gdx.graphics.glutils.PixmapTextureData
import com.badlogic.gdx.graphics.{Pixmap, Texture}
import com.sun.jna.Memory
import org.sgine._
import org.sgine.component.gdx.ActorIntegrated
import org.sgine.widget.{ComponentGroup, Image}
import pl.metastack.metarx.{ReadStateChannel, Sub, Var}
import uk.co.caprica.vlcj.player.direct.{BufferFormat, BufferFormatCallback, DirectMediaPlayer, RenderCallback}

import scala.annotation.tailrec

class MediaPlayer(val buffers: Int = 2)(implicit scrn: Screen) extends ComponentGroup()(scrn) with ActorIntegrated {
  private val videoImage = new Image
  private lazy val player = {
    val p = Media.factory.newDirectMediaPlayer(new VLCBufferFormatCallback(this), new VLCRenderCallback(this))
    p.addMediaPlayerEventListener(new PlayerListener(this))
    scrn.dispose.on {
      dispose()
    }
    p
  }
  private var texture: Texture = _

  private[video] val pbos = new AtomicInteger(0)
  private[video] val queue = new ConcurrentLinkedQueue[PBO]
  private[video] val readBuffer = new AtomicReference[PBO](null)

  private[video] val videoWidth = Var[Int](0)
  private[video] val videoHeight = Var[Int](0)

  private[video] val _media = Sub[Option[Media]](None)
  val media: ReadStateChannel[Option[Media]] = _media

  private[video] val _state = Sub[PlayerState](PlayerState.Stopped)
  val state: ReadStateChannel[PlayerState] = _state

  object status {
    private[video] val _position = Var[Double](0.0)
    private[video] val _time = Var[Double](0.0)

    val position: ReadStateChannel[Double] = _position
    val time: ReadStateChannel[Double] = _time

    private[video] def reset(): Unit = {
      _position := 0.0
      _time := 0L
    }
  }

  videoImage.size.width := size.width
  videoImage.size.height := size.height
  add(videoImage)

  media.attach { o =>
    status.reset()      // Reset the status information when the media changes
  }
  videoWidth.merge(videoHeight).attach { d =>
    render.once {
      if (texture != null) {
        texture.dispose()
      }
      val useMipMaps = true
      val disposePixmap = true
      texture = new Texture(new PixmapTextureData(new Pixmap(videoWidth.get, videoHeight.get, Pixmap.Format.RGBA8888), Pixmap.Format.RGBA8888, useMipMaps, disposePixmap))
      videoImage.drawable := Some(texture2Drawable(texture))

      // Clear the queue
      readBuffer.getAndSet(null) match {
        case null => // No read buffer set
        case pbo => pbo.dispose()
      }
      clearQueue()
      pbos.set(0)
    }
  }
  render.on {
    val current = pbos.get()
    if (videoWidth.get > 0 && videoHeight.get > 0 && current < buffers && pbos.compareAndSet(current, current + 1)) {
      queue.add(PBO(videoWidth.get, videoHeight.get))
    }

    readBuffer.getAndSet(null) match {
      case null => // Nothing to read
      case pbo => {
        pbo.updateTexture(texture.getTextureObjectHandle)
        pbo.updateBuffer()
        queue.add(pbo)
      }
    }
  }

  def onState(playerState: PlayerState)(f: => Unit): Unit = state.attach { s =>
    if (s == playerState) {
      f
    }
  }

  def load(resource: String): Unit = _media := Media.parse(resource, player)

  def play(): Unit = player.play()

  def pause(): Unit = player.pause()

  def stop(): Unit = player.stop()

  def seekPosition(percent: Double): Unit = player.setPosition(percent.toFloat)
  def seekTime(time: Double): Unit = player.setTime(math.round(time * 1000.0))

  def jump(time: Double): Unit = player.setTime(math.round((status.time.get + time) * 1000.0))

  def dispose(): Unit = {
    stop()
    clearQueue()
    player.release()
  }

  @tailrec
  private def clearQueue(): Unit = queue.poll() match {
    case null => // Finished
    case pbo => {
      pbo.dispose()
      clearQueue()
    }
  }
}

class VLCBufferFormatCallback(player: MediaPlayer) extends BufferFormatCallback {
  override def getBufferFormat(sourceWidth: Int, sourceHeight: Int): BufferFormat = {
    player.videoWidth := sourceWidth
    player.videoHeight := sourceHeight
    player.preferred._width := sourceWidth.toDouble
    player.preferred._height := sourceHeight.toDouble
    new BufferFormat("RGBA", sourceWidth, sourceHeight, Array(sourceWidth * 4), Array(sourceHeight))
  }
}

class VLCRenderCallback(player: MediaPlayer) extends RenderCallback {
  override def display(mediaPlayer: DirectMediaPlayer, nativeBuffers: Array[Memory], bufferFormat: BufferFormat): Unit = {
    player.queue.poll() match {
      case null => // No PBO available
      case pbo => {
        val videoBuffer = nativeBuffers.head.getByteBuffer(0, player.videoWidth.get * player.videoHeight.get * 4)
        pbo.buffer.put(videoBuffer)
        pbo.buffer.flip()
        val old = player.readBuffer.getAndSet(pbo)
        if (old != null) {
          player.queue.add(old)
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

sealed trait PlayerState

object PlayerState {
  case object Stopped extends PlayerState
  case object NewMedia extends PlayerState
  case object Opening extends PlayerState
  case object Playing extends PlayerState
  case object Error extends PlayerState
  case object Paused extends PlayerState
  case object Finished extends PlayerState
  case object Freed extends PlayerState
}