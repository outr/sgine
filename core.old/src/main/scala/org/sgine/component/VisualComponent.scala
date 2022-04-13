package org.sgine.component

import org.sgine.component.prop.ColorProperties
import reactify._

trait VisualComponent extends Component {
  val color = new ColorProperties

  val visible: Var[Boolean] = Var(true)
}