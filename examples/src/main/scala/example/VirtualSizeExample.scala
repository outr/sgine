package example

import org.sgine._
import org.sgine.lwjgl.BasicDesktopApp
import org.sgine.screen.VirtualSizeSupport
import org.sgine.widget.Image

object VirtualSizeExample extends BasicDesktopApp with VirtualSizeSupport {
  override def virtualWidth: Double = 1024.0
  override def virtualHeight: Double = 768.0

  create.on {
    this += new Image("1024.jpg") {
      size.width := 1024.0.vw
      size.height := 768.0.vh
    }
    this += new Image("sgine.png") {
      position.center := 512.0.vw
      position.middle := 385.0.vh
    }
  }
}
