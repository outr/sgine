package org.sgine.component

trait TypedContainer[C <: Component] extends AbstractContainer {
  override type Child = C
}

object TypedContainer {
  def apply[Child <: Component](children: Child*): TypedContainer[Child] = {
    val vector = children.toVector
    val container = new TypedContainer[Child] { self =>
      override val children: Children[Child] = Children(self, vector)
    }
    container
  }
}