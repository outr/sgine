package org.sgine.component.prop

import org.sgine.component.DimensionedComponent
import pl.metastack.metarx._

class PositionProperties(component: DimensionedComponent) {
  val x: Sub[Double] = Sub(0.0)
  val y: Sub[Double] = Sub(0.0)

  def left: Sub[Double] = x
  lazy val center: Dep[Double] = x.dep(_ - (component.size.width / 2.0), _ + (component.size.width.get / 2.0))
  lazy val right: Dep[Double] = x.dep(_ - component.size.width, _ + component.size.width.get)

  lazy val top: Dep[Double] = y.dep(_ - component.size.height, _ + component.size.height.get)
  lazy val middle: Dep[Double] = y.dep(_ - component.size.height, _ + (component.size.height.get / 2.0))
  def bottom: Sub[Double] = y
}