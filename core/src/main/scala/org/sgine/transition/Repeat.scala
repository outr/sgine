package org.sgine.transition

import org.sgine.Screen

class Repeat(val screen: Screen, times: Int = 1, transition: Transition) extends Transition {
  private var counter = 0

  override def init(): Unit = {
    counter = 0
    transition.init()
  }

  override def invoke(): Unit = {
    transition.invoke()
  }

  override def finished: Boolean = if (transition.finished) {
    if (counter == times) {
      true
    } else {
      counter += 1
      transition.init()
      false
    }
  } else {
    false
  }
}