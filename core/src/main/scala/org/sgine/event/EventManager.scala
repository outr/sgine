package org.sgine.event

import org.sgine._

import scala.collection.mutable.ListBuffer

class EventManager[Event] {
  private val queue = ListBuffer.empty[EventListener[Event]]

  def on(f: Event => Unit): EventListener[Event] = synchronized {
    val l = new EventListener[Event]((event: Event) => f(event), once = false)
    queue += l
    l
  }

  def once(f: Event => Unit): EventListener[Event] = synchronized {
    val l = new EventListener[Event]((event: Event) => f(event), once = true)
    queue += l
    l
  }

  def until(condition: => Boolean)(f: Event => Unit): EventListener[Event] = {
    var listener: EventListener[Event] = null
    listener = on { event =>
      f(event)
      if (condition) {
        remove(listener)
      }
    }
    listener
  }

  def remove(listener: EventListener[Event]) = synchronized {
    queue -= listener
  }

  def exec(event: Event): Unit = synchronized {
    queue.foreach { l =>
      UI().catchErrors {
        l.invoke(event)
        if (l.once) queue -= l
      }
    }
  }

  def nonEmpty: Boolean = queue.nonEmpty
  def isEmpty: Boolean = queue.isEmpty

  def clear(): Unit = queue.clear()
}

class EventListener[Event](f: Event => Unit, val once: Boolean) {
  def invoke(event: Event): Unit = f(event)
}