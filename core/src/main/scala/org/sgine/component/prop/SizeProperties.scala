package org.sgine.component.prop

import org.sgine.component.DimensionedComponent
import pl.metastack.metarx.Sub

class SizeProperties(component: DimensionedComponent) {
  val width: Sub[Double] = Sub(0.0)
  val height: Sub[Double] = Sub(0.0)
}