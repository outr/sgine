package org.sgine.component

import org.sgine.component.prop.{PositionProperties, SizeProperties}

trait DimensionedComponent extends Component {
  val position = new PositionProperties(this)
  val size = new SizeProperties(this)
}
