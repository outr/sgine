package org.sgine.transition

import org.sgine.Screen

trait Transition {
  def screen: Screen

  def finished: Boolean
  def invoke(): Unit

  def start() = screen.render.until(finished) {
    invoke()
  }

  def andThen(next: Transition): Sequence = new Sequence(screen, List(this, next))
  def and(other: Transition): Parallel = new Parallel(screen, Set(this, other))
}