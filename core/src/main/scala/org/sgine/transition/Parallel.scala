package org.sgine.transition

class Parallel(_transitions: Set[Transition]) extends Transition {
  private var transitions: Set[Transition] = _

  override def init(): Unit = {
    transitions = _transitions
    transitions.foreach(t => t.init())
  }

  override def finished: Boolean = {
    transitions = transitions.filterNot(_.finished)
    transitions.isEmpty
  }

  override def invoke(): Unit = transitions.foreach { t =>
    t.invoke()
  }

  override def and(other: Transition): Parallel = new Parallel(_transitions + other)

  override def toString: String = s"Parallel(${_transitions.mkString(", ")})"
}