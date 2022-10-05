package examples

import org.sgine.Color
import org.sgine.component.{Component, Image, PointerSupport}
import reactify._

object HelloSgine extends Example {
  override protected lazy val component: Component = new Image("sgine.png") with PointerSupport {
    center @= screen.center
    middle @= screen.middle

    color := (if (pointer.over) Color.Red else Color.White)
  }
}