package example

import org.sgine._
import org.sgine.lwjgl.BasicDesktopApp
import org.sgine.widget.RoundedRect

object RoundedRectExample extends BasicDesktopApp {
  this += new RoundedRect {
    position.center := ui.width / 2.0
    position.middle := ui.height / 2.0
    size.width := 70.pctw
    size.height := 70.pcth
    color := Color.LightBlue
  }
}