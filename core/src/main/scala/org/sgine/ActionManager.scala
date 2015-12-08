package org.sgine

import scala.collection.mutable.ListBuffer

class ActionManager {
  private val queue = ListBuffer.empty[Action]

  def on(f: => Unit): Action = synchronized {
    val a = new Action(() => f, once = false)
    queue += a
    a
  }

  def once(f: => Unit): Action = synchronized {
    val a = new Action(() => f, once = true)
    queue += a
    a
  }

  def exec(): Unit = synchronized {
    queue.foreach { a =>
      UI().catchErrors {
        a.invoke()
        if (a.once) queue -= a
      }
    }
  }

  def nonEmpty: Boolean = queue.nonEmpty
  def isEmpty: Boolean = queue.isEmpty

  def clear(): Unit = queue.clear()
}

class Action(f: () => Unit, val once: Boolean) {
  def invoke(): Unit = f()
}