package example

import org.sgine._
import org.sgine.lwjgl.BasicDesktopApp
import org.sgine.widget.{Label, TextInput}

object TextExample extends BasicDesktopApp with VirtualSizeSupport {
  virtualMode := VirtualMode.Bars

  ui.theme.font.family := "OpenSans"
  ui.theme.font.style := "Italic"
  ui.theme.font.size := 24.vf

  val longText = "Really crisp text that can dynamically size to the screen and properly wrap cleanly.\n\nIt's really easy to align and snap positioning to other dynamically sized elements."

  val heading = new Label("Hello Sgine!", "OpenSans", "Semibold", 120.vf) {
    position.center := ui.center
    position.top := ui.height - 50.0
  }
  val paragraph = new Label(longText) {
    position.center := ui.center
    position.top := heading.position.bottom - 50.0
    size.width := 500.vw
    wrap := true
  }
  val textInput1 = new TextInput("Editable Text") {
    color := Color.LightSkyBlue
    position.center := ui.center
    position.top := paragraph.position.bottom - 50.0
    size.width := 500.vw
    focus.on {
      println("1 received focus")
    }
    blur.on {
      println("1 blurred")
    }
  }
  val textInput2 = new TextInput("", "OpenSans", "Light", 20.vf) {
    position.center := ui.center
    position.top := textInput1.position.bottom - 50.0
    placeholder := "Hello World!"
    size.width := 500.vw
    focus.on {
      println("2 received focus")
    }
    blur.on {
      println("2 blurred")
    }
  }
  add(heading)
  add(paragraph)
  add(textInput1)
  add(textInput2)
}