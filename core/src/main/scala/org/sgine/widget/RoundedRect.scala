package org.sgine.widget

import org.sgine.Screen
import pl.metastack.metarx.Sub

class RoundedRect(implicit screen: Screen) extends Shape()(screen) {
  val radius: Sub[Double] = Sub[Double](25.0)
  val segments: Sub[Int] = Sub[Int](10)

  override def draw(): Unit = doFilled {
    // Top-Left
    arc(radius.get, size.height.get - radius.get, radius.get, 90.0, 90.0, segments.get)
    // Top-Right
    arc(size.width.get - radius.get, size.height.get - radius.get, radius.get, 0.0, 90.0, segments.get)
    // Bottom-Left
    arc(radius.get, radius.get, radius.get, 180.0, 90.0, segments.get)
    // Bottom-Right
    arc(size.width.get - radius.get, radius.get, radius.get, 270.0, 90.0, segments.get)
    // Top
    rect(radius.get, size.height.get - radius.get, size.width.get - (radius.get * 2.0), radius.get)
    // Middle
    rect(0.0, radius.get, size.width.get, size.height.get - (radius.get * 2.0))
    // Bottom
    rect(radius.get, 0.0, size.width.get - (radius.get * 2.0), radius.get)
  }
}
