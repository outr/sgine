package org.sgine.component

trait Container extends TypedContainer[Component] {
  def children: Children[Component]
}

object Container {
  def apply(children: Component*): Container = {
    val vector = children.toVector
    val container = new Container { self =>
      override val children: Children[Component] = Children(self, vector)
    }
    vector.foreach { child =>
      child._parent @= Some(container)
    }
    container
  }
}