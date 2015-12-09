package org.sgine.transition

import org.sgine.Screen

trait Transition {
  def screen: Screen

  def finished: Boolean
  def init(): Unit
  def invoke(): Unit

  def start() = {
    var first = true
    screen.render.until(finished) {
      if (first) {
        init()
        first = false
      }
      invoke()
    }
  }

  def andThen(next: Transition): Sequence = new Sequence(screen, List(this, next))
  def and(other: Transition): Parallel = new Parallel(screen, Set(this, other))
}