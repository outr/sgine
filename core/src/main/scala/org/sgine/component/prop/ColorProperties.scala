package org.sgine.component.prop

import org.sgine.Color
import pl.metastack.metarx.Sub

class ColorProperties {
  val red: Sub[Double] = Sub(1.0)
  val green: Sub[Double] = Sub(1.0)
  val blue: Sub[Double] = Sub(1.0)
  val alpha: Sub[Double] = Sub(1.0)

  def apply() = Color(red.get, green.get, blue.get, alpha.get)
  def :=(c: Color) = {
    red := c.red
    green := c.green
    blue := c.blue
    alpha := c.alpha
  }
  def :=(cp: ColorProperties): Unit = {
    red := cp.red
    green := cp.green
    blue := cp.blue
    alpha := cp.alpha
  }

  def update(color: com.badlogic.gdx.graphics.Color): Unit = {
    color.r = red.get.toFloat
    color.g = green.get.toFloat
    color.b = blue.get.toFloat
    color.a = alpha.get.toFloat
  }
}