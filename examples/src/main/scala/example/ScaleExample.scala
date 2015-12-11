package example

import org.sgine._
import org.sgine.lwjgl.BasicDesktopApp
import org.sgine.widget.Image

object ScaleExample extends BasicDesktopApp {
  create.on {
    this += new Image("1024.jpg")
    this += new Image("1024.jpg") {
      scale := 0.75
    }
    this += new Image("1024.jpg") {
      scale := 0.5
    }
  }
}