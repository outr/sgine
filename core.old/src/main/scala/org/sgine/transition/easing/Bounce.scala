package org.sgine.transition.easing

object Bounce {
  def easeIn(time: Double, start: Double, change: Double, duration: Double): Double = {
    change - easeOut(duration - time, 0, change, duration) + start
  }

  def easeOut(time: Double, start: Double, change: Double, duration: Double): Double = {
    val t = time / duration
    if (t < (1.0 / 2.75)) {
      change * (7.5625 * t * t) + start
    } else if (t < (2.0 / 2.75)) {
      val t2 = t - (1.5 / 2.75)
      change * (7.5625 * t2 * t2 + 0.75) + start
    } else if (t < (2.5 / 2.75)) {
      val t3 = t - (2.25 / 2.75)
      change * (7.5625 * t3 * t3 + 0.9375) + start
    } else {
      val t4 = t - (2.625 / 2.75)
      change * (7.5625 * t4 * t4 + 0.984375) + start
    }
  }

  def easeInOut(time: Double, start: Double, change: Double, duration: Double): Double = {
    if (time < duration / 2.0) {
      easeIn(time * 2.0, 0.0, change, duration) * 0.5 + start
    } else {
      easeOut(time * 2.0 - duration, 0.0, change, duration) * 0.5 + change * 0.5 + start
    }
  }
}