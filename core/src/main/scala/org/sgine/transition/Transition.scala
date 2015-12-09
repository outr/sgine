package org.sgine.transition

import org.sgine.Screen

trait Transition {
  def screen: Screen

  protected def continue: Boolean
  protected def invoke(): Unit

  def start() = screen.render.until(continue) {
    invoke()
  }
}