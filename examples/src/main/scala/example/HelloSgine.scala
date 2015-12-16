package example

import org.sgine._
import org.sgine.lwjgl.BasicDesktopApp
import org.sgine.widget.{Image, Label}

object HelloSgine extends BasicDesktopApp {
  this += new Image("sgine.png") {
    position.center := ui.width / 2.0
    position.middle := ui.height / 2.0
  }

  val label = new Label("Hello Sgine!", "OpenSans", "Semibold", 28) {
    position.center := ui.width / 2.0
    position.middle := ui.height / 2.0
  }
  add(label)

  delay(5.0) andThen function {
    println("Changing font!")
    label.font.size := 40
    label.color := Color.Red
  } start()
}