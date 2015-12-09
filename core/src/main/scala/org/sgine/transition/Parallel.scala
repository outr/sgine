package org.sgine.transition

import org.sgine.Screen

class Parallel(val screen: Screen, var transitions: Set[Transition]) extends Transition {
  // TODO: support restarting

  override def finished: Boolean = {
    transitions = transitions.filterNot(_.finished)
    transitions.isEmpty
  }

  override def invoke(): Unit = transitions.foreach { t =>
    t.invoke()
  }

  override def and(other: Transition): Parallel = new Parallel(screen, transitions + other)
}