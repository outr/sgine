package org.sgine.transition

class ActionTransition(action: () => Unit) extends Transition {
  private var hasRun = false

  override def init(): Unit = {
    hasRun = false
  }

  override def finished: Boolean = hasRun

  override def invoke(): Unit = {
    action()
    hasRun = true
  }
}