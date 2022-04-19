package org.sgine.component

import reactify.Val

trait Container extends TypedContainer[Component] {
  def children: Children[Component]
}

object Container {
  def apply(children: Component*): Container = {
    val list = children.toList
    val container = new Container { self =>
      override val children: Children[Component] = Children(self, list)
    }
    list.foreach { child =>
      child._parent @= Some(container)
    }
    container
  }
}