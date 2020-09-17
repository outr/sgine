package example

import org.sgine._
import org.sgine.lwjgl.BasicDesktopApp
import org.sgine.screen.FPSLoggingSupport
import org.sgine.widget.Image

object PositioningExample extends BasicDesktopApp with FPSLoggingSupport {
  ui.continuousRendering := true

  create.on {
    this += new Image("sgine.png") {    // Top-Left
      position.left := 50.0
      position.top := ui.height - 50.0
    }
    this += new Image("sgine.png") {    // Top-Right
      position.right := ui.width - 50.0
      position.top := ui.height - 50.0
    }
    this += new Image("sgine.png") {    // Bottom-Left
      position.left := 50.0
      position.bottom := 50.0
    }
    this += new Image("sgine.png") {    // Bottom-Right
      position.right := ui.width - 50.0
      position.bottom := 50.0
    }
    this += new Image("sgine.png") {    // Center
      position.center := ui.center
      position.middle := ui.middle
      size.maintainAspectRatio(width = ui.center)
    }
  }
}