package org.sgine.transition

import org.sgine.Screen

class Sequence(val screen: Screen, val _transitions: List[Transition]) extends Transition {
  private var transitions: List[Transition] = _

  override def init(): Unit = {
    transitions = _transitions
    transitions.head.init()
  }

  override def finished: Boolean = transitions.isEmpty

  override def invoke(): Unit = transitions.headOption match {
    case Some(t) => {
      t.invoke()
      if (t.finished) {
        transitions = transitions.tail
        transitions.headOption match {
          case Some(next) => next.init()
          case None => // Empty, ignore
        }
      }
    }
    case None => // Nothing to do
  }

  override def andThen(next: Transition): Sequence = new Sequence(screen, _transitions ::: List(next))
}
