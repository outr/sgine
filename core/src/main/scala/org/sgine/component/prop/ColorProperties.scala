package org.sgine.component.prop

import org.sgine.Color
import pl.metastack.metarx.Sub

class ColorProperties {
  val red: Sub[Double] = Sub(1.0)
  val green: Sub[Double] = Sub(1.0)
  val blue: Sub[Double] = Sub(1.0)
  val alpha: Sub[Double] = Sub(1.0)

  def apply() = Color(red.get, green.get, blue.get, alpha.get)
  def set(c: Color) = {
    red := c.red
    green := c.green
    blue := c.blue
    alpha := c.alpha
  }
}