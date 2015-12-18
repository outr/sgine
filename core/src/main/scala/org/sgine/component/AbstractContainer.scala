package org.sgine.component

import scala.collection.mutable.ArrayBuffer

trait AbstractContainer extends Component {
  private val children: ArrayBuffer[Component] = ArrayBuffer.empty[Component]

  protected def add[C <: Component](child: C): C = {
    screen.render.once {
      children += child
    }
    child
  }

  protected def remove[C <: Component](child: C): C = {
    screen.render.once {
      children -= child
    }
    child
  }
}