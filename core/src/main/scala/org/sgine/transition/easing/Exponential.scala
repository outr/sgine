package org.sgine.transition.easing

import scala.math._

object Exponential {
  def easeIn(time: Double, start: Double, change: Double, duration: Double): Double = {
    if (time == 0.0) {
      start
    } else {
      change * pow(2.0, 10.0 * (time / duration - 1.0)) + start
    }
  }

  def easeOut(time: Double, start: Double, change: Double, duration: Double): Double = {
    if (time == duration) {
      start + change
    } else {
      change * (-pow(2.0, -10.0 * time / duration) + 1.0) + start
    }
  }

  def easeInOut(time: Double, start: Double, change: Double, duration: Double): Double = {
    val t = time / (duration / 2.0)
    if (time == 0.0) {
      start
    } else if (time == duration) {
      start + change
    } else if (t < 1.0) {
      change / 2.0 * pow(2.0, 10.0 * (t - 1.0)) + start
    } else {
      change / 2.0 * (-pow(2.0, -10.0 * (t - 1.0)) + 2.0) + start
    }
  }
}