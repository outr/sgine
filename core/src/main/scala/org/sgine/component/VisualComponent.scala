package org.sgine.component

import org.sgine.component.prop.ColorProperties
import pl.metastack.metarx.Sub

trait VisualComponent extends Component {
  val color = new ColorProperties

  val visible: Sub[Boolean] = Sub(true)
}