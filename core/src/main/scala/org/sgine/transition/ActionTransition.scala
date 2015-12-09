package org.sgine.transition

import org.sgine.Screen

class ActionTransition(val screen: Screen, action: () => Unit) extends Transition {
  override def init(): Unit = {}

  override def finished: Boolean = true

  override def invoke(): Unit = action()
}