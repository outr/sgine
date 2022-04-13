package org.sgine.transition

class Sequence(val _transitions: List[Transition]) extends Transition {
  private var transitions: List[Transition] = _

  override def init(): Unit = {
    transitions = _transitions
    transitions.head.init()
  }

  override def finished: Boolean = transitions.headOption match {
    case Some(t) => if (t.finished) {
      transitions = transitions.tail
      transitions.headOption match {
        case Some(next) => {
          next.init()
          false
        }
        case None => true
      }
    } else {
      false
    }
    case None => true
  }

  override def invoke(): Unit = transitions.headOption match {
    case Some(t) => t.invoke()
    case None => // Nothing to do
  }

  override def andThen(next: Transition): Sequence = new Sequence(_transitions ::: List(next))

  override def toString: String = s"Sequence(${_transitions.mkString(", ")})"
}
