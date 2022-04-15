package org.sgine.component

import reactify.Val

trait TypedContainer[Child <: Component] extends Component {
  def children: Val[List[Child]]
}

object TypedContainer {
  def apply[Child <: Component](children: Child*): TypedContainer[Child] = {
    val list = children.toList
    val container = new TypedContainer[Child] {
      override val children: Val[List[Child]] = Val(list)
    }
    list.foreach { child =>
      child._parent @= Some(container)
    }
    container
  }

  def flatChildren(component: Component): List[Component] = component match {
    case container: TypedContainer[_] => container :: container.children.flatMap { child =>
      flatChildren(child)
    }
    case _ => List(component)
  }
}