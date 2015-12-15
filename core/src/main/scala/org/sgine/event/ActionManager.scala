package org.sgine.event

import java.util.concurrent.atomic.AtomicReference

import org.sgine._

import scala.collection.mutable.ListBuffer

class ActionManager(name: String) {
  private val queue = ListBuffer.empty[Action]

  def on(f: => Unit): Action = synchronized {
    val a = new Action(() => f, once = false)
    if (isActive) execAction(a)
    queue += a
    a
  }

  def once(f: => Unit): Action = synchronized {
    val a = new Action(() => f, once = true)
    if (isActive) execAction(a)
    queue += a
    a
  }

  def request[R](f: => R, timeout: Double = Double.MaxValue): R = {
    var result: Option[R] = None
    val action = once {
      result = Some(f)
    }
    waitFor(timeout) {
      result.isDefined
    }
    result match {
      case Some(r) => r
      case None => {
        remove(action)
        throw new RuntimeException(s"Request failed to complete within the given time ($timeout seconds).")
      }
    }
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
    ActionManager.current.set(name)
    try {
      queue.foreach(execAction)
    } finally {
      ActionManager.current.set("")
    }
  }

  private val execAction: Action => Unit = (action: Action) => UI().catchErrors {
    action.invoke()
    if (action.once) {
      queue -= action
    }
  }

  def nonEmpty: Boolean = queue.nonEmpty
  def isEmpty: Boolean = queue.isEmpty

  def clear(): Unit = queue.clear()

  def isActive: Boolean = ActionManager.current.get() == name
}

class Action(f: () => Unit, val once: Boolean) {
  def invoke(): Unit = f()
}

object ActionManager {
  private val current = new AtomicReference[String]("")
}