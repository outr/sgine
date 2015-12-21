package org.sgine.component.prop

import pl.metastack.metarx.Sub

class ClippingProperties {
  val top: Sub[Double] = Sub[Double](0.0)
  val bottom: Sub[Double] = Sub[Double](0.0)
  val left: Sub[Double] = Sub[Double](0.0)
  val right: Sub[Double] = Sub[Double](0.0)
  val enabled: Sub[Boolean] = Sub[Boolean](false)
}
