package org.sgine.component

import reactify.Val

trait Component {

}

trait Container {
  def children: Val[List[Component]]
}

object Component {
  def flatChildren(component: Component): List[Component] = component match {
    case container: Container => container :: container.children.flatMap { child =>
      flatChildren(child)
    }
    case _ => List(component)
  }
}