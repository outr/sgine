package org.sgine.component

import org.sgine.component.prop.ColorProperties
import pl.metastack.metarx.ReadChannel

trait VisualComponent extends Component {
  val color = new ColorProperties

  val visible: ReadChannel[Boolean] = color.alpha.map(_ > 0.0)
}