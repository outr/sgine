package example

import org.sgine._
import org.sgine.lwjgl.BasicDesktopApp
import org.sgine.screen.VirtualSizeSupport
import org.sgine.widget.Label
import pl.metastack.metarx._

object KeyExample extends BasicDesktopApp with VirtualSizeSupport {
  val keyUp = new Label("Last Key Up: None", "OpenSans", "Semibold", 60.vf) {
    position.center := ui.center
    position.middle := ui.middle
  }
  val keyDown = new Label("Last Key Down: None", "OpenSans", "Semibold", 60.vf) {
    position.center := ui.center
    position.bottom := (keyUp.position.top + 20.0)
  }
  val keyTyped = new Label("Last Key Typed: None", "OpenSans", "Semibold", 60.vf) {
    position.center := ui.center
    position.top := (keyUp.position.bottom - 20.0)
  }

  add(keyUp)
  add(keyDown)
  add(keyTyped)

  key.down.attach { evt =>
    keyDown.text := s"Last Key Down: ${evt.key}"
  }
  key.up.attach { evt =>
    keyUp.text := s"Last Key Up: ${evt.key}"
  }
  key.typed.attach { evt =>
    keyTyped.text := s"Last Key Typed: ${evt.key}"
  }
}