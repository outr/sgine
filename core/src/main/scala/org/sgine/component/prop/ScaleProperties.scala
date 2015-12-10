package org.sgine.component.prop

import pl.metastack.metarx.{ReadChannel, Sub}

class ScaleProperties {
  val x: Sub[Double] = Sub(1.0)
  val y: Sub[Double] = Sub(1.0)

  def :=(d: Double) = {
    x := d
    y := d
  }
  def :=(c: ReadChannel[Double]) = {
    x := c
    y := c
  }
}
