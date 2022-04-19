package org.sgine.transition.easing

object Linear {
  def easeIn(time: Double, start: Double, change: Double, duration: Double): Double = {
    change * time / duration + start
  }

  def easeOut(time: Double, start: Double, change: Double, duration: Double): Double = {
    change * time / duration + start
  }

  def easeInOut(time: Double, start: Double, change: Double, duration: Double): Double = {
    change * time / duration + start
  }
}