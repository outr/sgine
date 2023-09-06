package org.sgine.audio

class Sound(private[audio] val gdx: com.badlogic.gdx.audio.Sound) extends AudioElement {
  def play(volume: Double = 1.0,
           pitch: Double = 1.0,
           pan: Double = 0.0,
           loop: Boolean = false): SoundInstance = {
    val id = if (loop) {
      gdx.loop(volume.toFloat, pitch.toFloat, pan.toFloat)
    } else {
      gdx.play(volume.toFloat, pitch.toFloat, pan.toFloat)
    }
    new SoundInstance(this, id, volume, pitch, pan, loop)
  }

  def playAndForget(volume: Double = 1.0,
                    pitch: Double = 1.0,
                    pan: Double = 0.0,
                    loop: Boolean = false): Unit = {
    if (loop) {
      gdx.loop(volume.toFloat, pitch.toFloat, pan.toFloat)
    } else {
      gdx.play(volume.toFloat, pitch.toFloat, pan.toFloat)
    }
  }

  def stop(): Unit = gdx.stop()

  def pause(): Unit = gdx.pause()

  def resume(): Unit = gdx.resume()

  override def dispose(): Unit = {
    gdx.dispose()
    super.dispose()
  }
}
