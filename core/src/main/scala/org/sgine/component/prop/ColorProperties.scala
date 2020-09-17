package org.sgine.component.prop

import io.youi.Color
import reactify._

class ColorProperties {
  val red: Var[Double] = Var(1.0)
  val green: Var[Double] = Var(1.0)
  val blue: Var[Double] = Var(1.0)
  val alpha: Var[Double] = Var(1.0)

  def apply(): Color = Color.fromRGBA(red.get, green.get, blue.get, alpha.get)
  def :=(c: Color): Unit = {
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