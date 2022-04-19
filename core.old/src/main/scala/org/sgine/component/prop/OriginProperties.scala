package org.sgine.component.prop

import org.sgine.component.DimensionedComponent
import reactify._

class OriginProperties(component: DimensionedComponent) {
  val x: Var[Double] = Var(0.0)
  val y: Var[Double] = Var(0.0)

  x := component.size.width / 2.0
  y := component.size.height / 2.0
}