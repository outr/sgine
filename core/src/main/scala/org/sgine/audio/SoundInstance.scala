package org.sgine.audio

class SoundInstance(sound: Sound, id: Long, vlm: Double, ptch: Double, pn: Double, lp: Boolean) {
  private var _volume = vlm
  private var _pitch = ptch
  private var _pan = pn
  private var _loop = lp

  def volume: Double = _volume

  def volume_=(v: Double): Unit = {
    _volume = v
    sound.gdx.setVolume(id, v.toFloat)
  }

  def pitch: Double = _pitch

  def pitch_=(p: Double): Unit = {
    _pitch = p
    sound.gdx.setPitch(id, p.toFloat)
  }

  def pan: Double = _pan

  def pan_=(p: Double): Unit = {
    _pan = p
    sound.gdx.setPan(id, p.toFloat, volume.toFloat)
  }

  def loop: Boolean = _loop

  def loop_=(l: Boolean): Unit = {
    _loop = l
    sound.gdx.setLooping(id, l)
  }

  def stop(): Unit = sound.gdx.stop(id)

  def pause(): Unit = sound.gdx.pause(id)

  def resume(): Unit = sound.gdx.resume(id)
}
