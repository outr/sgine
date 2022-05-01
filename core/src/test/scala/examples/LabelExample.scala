package examples

import org.sgine.Color
import org.sgine.component.{Component, InteractiveComponent, Label}
import reactify._

object LabelExample extends Example {
  override protected lazy val component: Component = new Label("Hello, World!") with InteractiveComponent {
    font @= Fonts.Pacifico.normal
    center @= screen.center
    middle @= screen.middle

    color := (if (pointer.over) Color.Red else Color.White)

    override def toString: String = "text"
  }
}