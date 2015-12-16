package example

import org.sgine._
import org.sgine.lwjgl.BasicDesktopApp
import org.sgine.widget.Shape

object ShapeExample extends BasicDesktopApp {
  this += new Shape {
    position.center := ui.width / 2.0
    position.middle := ui.height / 2.0
    size.width := 200.0
    size.height := 200.0
    color := Color.Red
  }
}
