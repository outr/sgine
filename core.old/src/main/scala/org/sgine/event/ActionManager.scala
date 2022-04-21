package org.sgine.event

import java.util.concurrent.atomic.AtomicReference

import org.sgine._

class ActionManager(name: String) {
  private var queue = List.empty[Action]

  private def add(action: Action): Unit = synchronized {
    queue = queue ::: List(action)
  }

  def remove(action: Action): Unit = synchronized {
    queue = queue.filterNot(_ eq action)
  }

  def on(f: => Unit): Action = synchronized {
    val a = new Action(() => f, once = false)
    if (isActive) execAction(a)
    add(a)
    a
  }

  def once(f: => Unit): Action = synchronized {
    val a = new Action(() => f, once = true)
    if (isActive) {
      execAction(a)
    } else {
      add(a)
    }
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
      remove(action)
    }
  }

  def nonEmpty: Boolean = queue.nonEmpty
  def isEmpty: Boolean = queue.isEmpty

  def clear(): Unit = synchronized {
    queue = Nil
  }

  // TODO: investigate why this sometimes doesn't work right
  def isActive: Boolean = false //ActionManager.current.get() == name
}

class Action(f: () => Unit, val once: Boolean) {
  def invoke(): Unit = f()
}

object ActionManager {
  private val current = new AtomicReference[String]("")
}