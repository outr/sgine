package org.sgine.component

import reactify.Val

trait Container extends TypedContainer[Component] {
  def children: Val[List[Component]]
}

object Container {
  def apply(children: Component*): Container = {
    val list = children.toList
    val container = new Container {
      override val children: Val[List[Component]] = Val(list)
    }
    list.foreach { child =>
      child._parent @= Some(container)
    }
    container
  }
}