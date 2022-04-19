package org.sgine.component

import reactify.{Channel, Val}

trait TypedContainer[Child <: Component] extends Component {
  def children: Children[Child]
}

object TypedContainer {
  def apply[Child <: Component](children: Child*): TypedContainer[Child] = {
    val list = children.toList
    val container = new TypedContainer[Child] { self =>
      override val children: Children[Child] = Children(self, list)
    }
    container
  }

  def flatChildren(components: Component*): List[Component] = components.toList.flatMap {
    case container: TypedContainer[_] => container :: container.children.flatMap { child =>
      flatChildren(child)
    }
    case component => List(component)
  }
}