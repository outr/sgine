package org.sgine.task

import org.sgine.easing.Easing

import scala.concurrent.duration._

case class PartialAnimate[T](get: () => T,
                             apply: T => Unit,
                             destination: () => T,
                             animatable: Animatable[T]) {
  def from(value: => T): PartialAnimate[T] = copy(get = () => value)

  def in(duration: => FiniteDuration): AnimateIn[T] =
    AnimateIn(get, apply, destination, () => duration, Easing.linear, animatable)

  // TODO: Figure out how to solve this?
//  def by(stepBy: Double): AnimateIn[T] = {
//    val duration = () => ((math.abs(get() - destination()) * 1000.0) / stepBy).millis
//    AnimateIn(get, apply, destination, duration, Easing.linear, animatable)
//  }
}