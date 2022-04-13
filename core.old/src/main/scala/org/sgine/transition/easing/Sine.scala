package org.sgine.transition.easing

import scala.math._

object Sine {
  def easeIn(time: Double, start: Double, change: Double, duration: Double): Double = {
    -change * cos(time / duration * (Pi / 2.0)) + change + start
  }

  def easeOut(time: Double, start: Double, change: Double, duration: Double): Double = {
    change * sin(time / duration * (Pi / 2.0)) + start
  }

  def easeInOut(time: Double, start: Double, change: Double, duration: Double): Double = {
    -change / 2.0 * (cos(Pi * time / duration) - 1.0) + start
  }
}