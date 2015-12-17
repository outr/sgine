package org.sgine.component

import pl.metastack.metarx.Buffer

trait AbstractContainer extends Component {
  private val children: Buffer[Component] = Buffer[Component]

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