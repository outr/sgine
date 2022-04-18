package org.sgine.component

import reactify.{Channel, Val}

trait TypedContainer[Child <: Component] extends Component {
  def children: Children[Child]
}

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

    set(children)
    children.foreach { child =>
      added @= child
    }
  }
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