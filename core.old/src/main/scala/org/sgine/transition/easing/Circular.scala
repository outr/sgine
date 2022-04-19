package org.sgine.transition.easing

import scala.math._

object Circular {
  def easeIn(time: Double, start: Double, change: Double, duration: Double): Double = {
    val t = time / duration
    -change * (sqrt(1.0 - t * t) - 1.0) + start
  }

  def easeOut(time: Double, start: Double, change: Double, duration: Double): Double = {
    val t = (time / duration) - 1.0
    change * sqrt(1.0 - t * t) + start
  }

  def easeInOut(time: Double, start: Double, change: Double, duration: Double): Double = {
    val t = time / (duration / 2)
    if (t < 1) {
      -change / 2.0 * (sqrt(1.0 - t * t) - 1.0) + start
    } else {
      val t2 = t - 2.0
      change / 2.0 * (sqrt(1.0 - t2 * t2) + 1.0) + start
    }
  }
}