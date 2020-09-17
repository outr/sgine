package org.sgine.component.prop

import reactify._

class ClippingProperties {
  val top: Var[Double] = Var[Double](0.0)
  val bottom: Var[Double] = Var[Double](0.0)
  val left: Var[Double] = Var[Double](0.0)
  val right: Var[Double] = Var[Double](0.0)
  val enabled: Var[Boolean] = Var[Boolean](false)
}
