package example

import org.sgine.component.{Component, Image, PointerSupport}
import org.sgine.{Color, PointerCursor}
import reactify._

object HelloSgine extends Example {
  override protected lazy val component: Component = new Image("sgine.png") with PointerSupport {
    center := screen.center
    middle := screen.middle

    color := (if (pointer.isOver) Color.Red else Color.White)
    pointer.cursor @= Some(PointerCursor.Hand)
  }
}