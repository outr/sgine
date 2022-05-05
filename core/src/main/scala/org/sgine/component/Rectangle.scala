package org.sgine.component

import org.sgine.drawable.{Drawer, ShapeDrawable}
import reactify._

class Rectangle extends Image { image =>
  drawable @= new ShapeDrawable {
    override def draw(drawer: Drawer): Unit = {
      drawer.color = image.color
      drawer.filled.rectangle()
    }

    override def width: Double = image.width

    override def height: Double = image.height
  }
}