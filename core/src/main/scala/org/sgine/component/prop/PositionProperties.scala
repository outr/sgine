package org.sgine.component.prop

import org.sgine.component.DimensionedComponent
import pl.metastack.metarx._

class PositionProperties(component: DimensionedComponent) {
  val x: Sub[Double] = Sub(0.0)
  val y: Sub[Double] = Sub(0.0)

  def left: Sub[Double] = x
  lazy val center: DependentVar = new DependentVar(
    x,
    (channel: ReadChannel[Double]) => channel - (component.size.width / 2.0),
    x.get + (component.size.width.get / 2.0)
  )
  lazy val right: DependentVar = new DependentVar(
    x,
    (channel: ReadChannel[Double]) => channel - component.size.width,
    x.get + component.size.width.get
  )

  lazy val top: DependentVar = new DependentVar(
    y,
    (channel: ReadChannel[Double]) => channel - component.size.height,
    y.get + component.size.height.get
  )
  lazy val middle: DependentVar = new DependentVar(
    y,
    (channel: ReadChannel[Double]) => channel - (component.size.height / 2.0),
    y.get + (component.size.height.get / 2.0)
  )
  def bottom: Sub[Double] = y
}