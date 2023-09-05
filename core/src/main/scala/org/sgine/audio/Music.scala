package org.sgine.audio

import com.badlogic.gdx.audio
import reactify.{Trigger, Val, Var}

class Music(gdx: com.badlogic.gdx.audio.Music) extends AudioElement {
  private val _playing: Var[Boolean] = Var(false)
  private val _position: Var[Double] = Var(0.0)

  val position: Val[Double] = _position

  def position_=(v: Double): Unit = gdx.setPosition(v.toFloat)

  lazy val looping: Var[Boolean] = {
    val v = Var(gdx.isLooping)
    v.attach(gdx.setLooping)
    v
  }

  lazy val volume: Var[Double] = {
    val v = Var(gdx.getVolume.toDouble)
    v.attach(d => gdx.setVolume(d.toFloat))
    v
  }

  lazy val pan: Var[Double] = {
    val v = Var(0.0)
    v.attach(d => gdx.setPan(d.toFloat, volume().toFloat))
    v
  }

  val finished: Trigger = Trigger()

  def play(): Unit = gdx.play()

  def pause(): Unit = gdx.pause()

  def stop(): Unit = gdx.stop()

  gdx.setOnCompletionListener((_: audio.Music) => finished.trigger())

  override def update(delta: Double): Unit = {
    super.update(delta)

    _playing @= gdx.isPlaying
    _position @= gdx.getPosition.toDouble
  }

  override def dispose(): Unit = {
    gdx.dispose()
    super.dispose()
  }
}
