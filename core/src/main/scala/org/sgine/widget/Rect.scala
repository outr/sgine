package org.sgine.widget

import org.sgine._
import org.sgine.component.prop.ColorProperties

class Rect(implicit screen: Screen) extends Shape()(screen) {
  object colors {
    val topLeft: ColorProperties = new ColorProperties
    val topRight: ColorProperties = new ColorProperties
    val bottomLeft: ColorProperties = new ColorProperties
    val bottomRight: ColorProperties = new ColorProperties

    topLeft := color
    topRight := color
    bottomLeft := color
    bottomRight := color
  }

  override def draw(): Unit = doFilled {
    colors.topLeft.update(c4)
    colors.topRight.update(c3)
    colors.bottomLeft.update(c1)
    colors.bottomRight.update(c2)
    rect(0.0, 0.0, size.width.get, size.height.get)
  }
}