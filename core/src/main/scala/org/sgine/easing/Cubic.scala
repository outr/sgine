package org.sgine.easing

object Cubic {
  def easeIn(time: Double, start: Double, change: Double, duration: Double): Double = {
    val t = time / duration
    change * t * t * t + start
  }

  def easeOut(time: Double, start: Double, change: Double, duration: Double): Double = {
    val t = time / duration - 1.0
    change * (t * t * t + 1.0) + start
  }

  def easeInOut(time: Double, start: Double, change: Double, duration: Double): Double = {
    val t = time / (duration / 2.0)
    if (t < 1.0) {
      change / 2.0 * t * t * t + start
    } else {
      val t2 = t - 2.0
      change / 2.0 * (t2 * t2 * t2 + 2.0) + start
    }
  }
}
