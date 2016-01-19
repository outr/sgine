package example

import org.sgine._
import org.sgine.lwjgl.BasicDesktopApp
import org.sgine.render.MouseHideSupport
import org.sgine.widget.Label

object MouseHideExample extends BasicDesktopApp with MouseHideSupport {
  create.on {
    this += new Label("Mouse is Showing", "OpenSans", "Regular", 32) {
      position.center := ui.center
      position.middle := ui.middle

      mouseHide.mouseShowing.attach { b =>
        text := (if (b) "Mouse is Showing" else "Mouse is Hidden")
      }
    }
  }
}
