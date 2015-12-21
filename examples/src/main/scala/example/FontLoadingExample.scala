package example

import org.sgine._
import org.sgine.lwjgl.BasicDesktopApp
import org.sgine.screen.VirtualSizeSupport
import org.sgine.widget.Label

object FontLoadingExample extends BasicDesktopApp with VirtualSizeSupport {
  this += new Label("Font Loading Example", "Pacifico", "Normal", 80.vf) {
    position.center := ui.width / 2.0
    position.middle := ui.height / 2.0
  }
}
