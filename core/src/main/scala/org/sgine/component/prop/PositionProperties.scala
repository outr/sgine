package org.sgine.component.prop

import org.sgine.component.DimensionedComponent
import reactify._

class PositionProperties(component: DimensionedComponent) {
  val x: Var[Double] = Var(0.0)
  val y: Var[Double] = Var(0.0)

  def left: Var[Double] = x
  lazy val center: Dep[Double, Double] = Dep(x)(_ + (component.size.width / 2.0), _ - (component.size.width / 2.0))
  lazy val right: Dep[Double, Double] = Dep(x)(_ + component.size.width, _ - component.size.width)

  lazy val top: Dep[Double, Double] = Dep(y)(_ + component.size.height, _ - component.size.height)
  lazy val middle: Dep[Double, Double] = Dep(y)(_ + (component.size.height / 2.0), _ - (component.size.height / 2.0))
  def bottom: Var[Double] = y
}