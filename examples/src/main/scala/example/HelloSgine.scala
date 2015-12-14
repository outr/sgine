package example

import org.sgine._
import org.sgine.lwjgl.BasicDesktopApp
import org.sgine.widget.Image

object HelloSgine extends BasicDesktopApp {
  this += new Image("sgine.png") {
    position.center := ui.width / 2.0
    position.middle := ui.height / 2.0
  }
}