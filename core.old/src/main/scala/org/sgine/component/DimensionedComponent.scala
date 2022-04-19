package org.sgine.component

import org.sgine.component.prop._
import reactify._

trait DimensionedComponent extends Component {
  val position = new PositionProperties(this)
  val size = new SizeProperties(this)
  val rotation: Var[Double] = Var(0.0)
  val origin = new OriginProperties(this)
  val scale = new ScaleProperties
  val clip = new ClippingProperties
  val preferred: PreferredSize = new PreferredSize
}
