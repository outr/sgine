package examples

import org.sgine.component.{Component, Label}
import org.sgine.event.MouseHideSupport

object MouseHideExample extends Example with MouseHideSupport {
  override protected def component: Component = new Label {
    text @= "Mouse is Showing"
    font @= Fonts.Pacifico.normal

    center := screen.center
    middle := screen.middle

    mouseHide.mouseShowing.attach { b =>
      text := (if (b) "Mouse is Showing" else "Mouse is Hidden")
    }
  }
}
