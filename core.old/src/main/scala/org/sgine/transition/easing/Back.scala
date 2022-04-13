package org.sgine.transition.easing

object Back {
  private val overshoot = 1.70158d

  def easeIn(time: Double, start: Double, change: Double, duration: Double): Double = {
    val t = time / duration
    change * t * t * ((overshoot + 1.0) * t - overshoot) + start
  }

  def easeOut(time: Double, start: Double, change: Double, duration: Double): Double = {
    val t = time / duration - 1
    change * (t * t * ((overshoot + 1.0) * t + overshoot) + 1) + start
  }

  def easeInOut(time: Double, start: Double, change: Double, duration: Double): Double = {
    val t = time / (duration / 2.0)
    val o = overshoot * 1.525
    if (t < 1.0) {
      change / 2.0 * (t * t * ((o + 1.0) * t - o)) + start
    } else {
      val t2 = t - 2.0
      change / 2.0 * (t2 * t2 * ((o + 1.0) * t2 + o) + 2.0) + start
    }
  }
}