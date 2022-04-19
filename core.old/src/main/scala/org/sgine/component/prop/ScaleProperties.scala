package org.sgine.component.prop

import reactify._

class ScaleProperties {
  val x: Var[Double] = Var(1.0)
  val y: Var[Double] = Var(1.0)

  def :=(d: Double): Unit = {
    x := d
    y := d
  }
  def :=(c: Val[Double]): Unit = {
    x := c
    y := c
  }
}
