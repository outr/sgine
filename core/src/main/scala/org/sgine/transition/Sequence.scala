package org.sgine.transition

import org.sgine.Screen

class Sequence(val screen: Screen, var transitions: List[Transition]) extends Transition {
  override def finished: Boolean = transitions.isEmpty

  override def invoke(): Unit = transitions.headOption match {
    case Some(t) => {
      t.invoke()
      if (t.finished) {
        transitions = transitions.tail
      }
    }
    case None => // Nothing to do
  }

  override def andThen(next: Transition): Sequence = new Sequence(screen, transitions ::: List(next))
}
