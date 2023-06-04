package org.sgine.component

trait TypedContainer[C <: Component] extends AbstractContainer {
  override type Child = C
}

object TypedContainer {
  def apply[C <: Component](children: C*): TypedContainer[C] = {
    val vector = children.toVector
    val container = new TypedContainer[C] { self =>
      override val children: Children[C] = Children(self, vector)
    }
    container
  }
}