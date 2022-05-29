package examples

import org.sgine.Color
import org.sgine.component.{Component, InteractiveComponent, Label}
import reactify._

object LabelExample extends Example {
  override protected lazy val component: Component = new Label("Hello, [#00ff00ff]World![]\n\nTesting, this is some [#00ffffff]really long text[] that should wrap multiple lines!") with InteractiveComponent {
    font @= Fonts.Pacifico.normal
    center @= screen.center
    middle @= screen.middle
    wrap @= true
    width @= 1000.0

    color := (if (pointer.over) Color.Red else Color.White)

    override def toString: String = "text"
  }
}