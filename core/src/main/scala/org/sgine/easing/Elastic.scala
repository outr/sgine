package org.sgine.easing

object Elastic {
  def easeIn(time: Double, start: Double, change: Double, duration: Double): Double = {
    val t = time / duration
    if (time == 0.0) {
      start
    } else if (t == 1.0) {
      start + change
    } else {
      val period = duration * 0.3
      val amplitude = change
      val s = period / 4.0
      val t2 = t - 1.0
      -(amplitude * math.pow(2.0, 10.0 * t2) * math.sin((t2 * duration - s) * (2.0 * math.Pi) / period)) + start
    }
  }

  def easeOut(time: Double, start: Double, change: Double, duration: Double): Double = {
    val t = time / duration
    if (time == 0.0) {
      start
    } else if (t == 1.0) {
      start + change
    } else {
      val period = duration * 0.3
      val amplitude = change
      val s = period / 4.0

      amplitude * math.pow(2.0, -10.0 * t) * math.sin((t * duration - s) * (2.0 * math.Pi) / period) + change + start
    }
  }

  def easeInOut(time: Double, start: Double, change: Double, duration: Double): Double = {
    val t = time / (duration / 2.0)
    if (time == 0.0) {
      start
    } else if (t == 2.0) {
      start + change
    } else {
      val period = duration * (0.3 * 1.5)
      val amplitude = change
      val s = period / 4.0
      val t2 = t - 1.0

      if (t < 1.0) {
        -0.5 * (amplitude * math.pow(2.0, 10.0 * t2) * math.sin((t2 * duration - s) * (2.0 * math.Pi) / period)) + start
      } else {
        amplitude * math.pow(2.0, -10.0 * t2) * math.sin((t2 * duration - s) * (2.0 * math.Pi) / period) * 0.5 + change + start
      }
    }
  }
}
