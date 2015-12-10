package org.sgine.component.prop

import org.sgine.component.DimensionedComponent
import pl.metastack.metarx.Sub

class OriginProperties(component: DimensionedComponent) {
  val x: Sub[Double] = Sub(0.0)
  val y: Sub[Double] = Sub(0.0)

  x := component.size.width / 2.0
  y := component.size.height / 2.0
}