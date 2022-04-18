package org.sgine.component

import reactify.{Channel, Val}

trait Children[Child <: Component] extends Val[List[Child]] {
  protected def container: Component

  val added: Channel[Child] = Channel[Child]
  val removed: Channel[Child] = Channel[Child]

  changes {
    case (oldValue, newValue) =>
      val rem = oldValue.diff(newValue)
      val add = newValue.diff(oldValue)
      rem.foreach(child => removed @= child)
      add.foreach(child => added @= child)
  }

  removed.attach { child =>
    child._parent @= None
  }
  added.attach { child =>
    child._parent @= Some(container)
  }
}

object Children {
  def apply[Child <: Component](parent: Component, children: List[Child]): Children[Child] = new Children[Child] {
    override protected def container: Component = parent

    set(Nil)
    set(children)
  }
}