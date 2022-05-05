package org.sgine.task

trait Animatable[T] {
  def valueAt(start: T, end: T, position: Double): T
}