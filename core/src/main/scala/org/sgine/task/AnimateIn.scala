package org.sgine.task

import org.sgine.easing.Easing

import scala.concurrent.duration.FiniteDuration

case class AnimateIn[T](get: () => T,
                        apply: T => Unit,
                        destination: () => T,
                        duration: () => FiniteDuration,
                        easing: Easing,
                        animatable: Animatable[T]) extends DurationTask {
  private var initialPosition: T = get()

  def easing(easing: Easing): AnimateIn[T] = copy(easing = easing)

  override def time: FiniteDuration = duration()

  override def act(delta: Double, elapsed: Double, progress: Double, reset: Boolean): Unit = {
    if (reset) {
      initialPosition = get()
    }
    val eased = easing.calculate(progress)
    val value = animatable.valueAt(initialPosition, destination(), eased)
    apply(value)
  }
}