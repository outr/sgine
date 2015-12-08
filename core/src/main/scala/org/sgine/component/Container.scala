package org.sgine.component

import pl.metastack.metarx.Buffer

trait Container extends Component {
  private val children: Buffer[Component] = Buffer[Component]

  protected def add(child: Component): Unit = screen.render.once {
    children += child
  }

  protected def remove(child: Component): Unit = screen.render.once {
    children -= child
  }
}