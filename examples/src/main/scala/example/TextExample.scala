package example

import org.sgine._
import org.sgine.lwjgl.BasicDesktopApp
import org.sgine.screen.{VirtualMode, VirtualSizeSupport}
import org.sgine.widget.Label

object TextExample extends BasicDesktopApp with VirtualSizeSupport {
  override val virtualWidth: Double = 1024.0
  override val virtualHeight: Double = 768.0
  override val virtualMode: VirtualMode = VirtualMode.Stretch

  val longText = "Really crisp text that can dynamically size to the screen and properly wrap cleanly.\n\nIt's really easy to align and snap positioning to other dynamically sized elements."

  val heading = new Label("Hello Sgine!", "OpenSans", "Semibold", 120.vf) {
    position.center := ui.width / 2.0
    position.top := ui.height - 50.0
  }
  val paragraph = new Label(longText, "OpenSans", "Light", 24.vf) {
    position.center := ui.width / 2.0
    position.top := heading.position.bottom - 50.0
    size.width := 500.vw
    wrap := true
  }
  add(heading)
  add(paragraph)
}