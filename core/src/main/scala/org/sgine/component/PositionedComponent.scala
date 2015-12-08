package org.sgine.component

import org.sgine.component.prop.PositionProperties

trait PositionedComponent extends Component {
  val position = new PositionProperties
}
