package org.sgine.easing

object Sine {
  def easeIn(time: Double, start: Double, change: Double, duration: Double): Double = {
    -change * math.cos(time / duration * (math.Pi / 2.0)) + change + start
  }

  def easeOut(time: Double, start: Double, change: Double, duration: Double): Double = {
    change * math.sin(time / duration * (math.Pi / 2.0)) + start
  }

  def easeInOut(time: Double, start: Double, change: Double, duration: Double): Double = {
    -change / 2.0 * (math.cos(math.Pi * time / duration) - 1.0) + start
  }
}
