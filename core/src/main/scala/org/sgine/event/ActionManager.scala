package org.sgine.event

import org.sgine._

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

  def until(condition: => Boolean)(f: => Unit): Action = {
    var action: Action = null
    action = on {
      f
      if (condition) {
        remove(action)
      }
    }
    action
  }

  def in(delay: Double)(f: => Unit): Action = every(delay, stopIn = delay)(f)

  def every(delay: Double, runNow: Boolean = false, stopIn: Double = Int.MaxValue.toDouble)(f: => Unit): Action = {
    var total = 0.0
    var elapsed = if (runNow) delay else 0.0
    var action: Action = null
    action = on {
      elapsed += ui.delta
      total += ui.delta
      if (elapsed >= delay) {
        f
        elapsed = 0.0
      }
      if (total > stopIn) {
        remove(action)
      }
    }
    action
  }

  def remove(action: Action) = synchronized {
    queue -= action
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