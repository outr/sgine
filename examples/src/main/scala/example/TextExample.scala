package example

import org.sgine._
import org.sgine.lwjgl.BasicDesktopApp
import org.sgine.screen.{VirtualMode, VirtualSizeSupport}
import org.sgine.widget.Label

object TextExample extends BasicDesktopApp with VirtualSizeSupport {
  override val virtualWidth: Double = 1024.0
  override val virtualHeight: Double = 768.0
  override val virtualMode: VirtualMode = VirtualMode.Stretch

  this += new Label("Hello Sgine!", "OpenSans", "Semibold", 120.vf) {
    position.center := ui.width / 2.0
    position.middle := ui.height / 2.0
  }
}