package example

import org.sgine.Color
import org.sgine.component.{Component, Container, Label, PointerSupport}
import reactify._

object LabelExample extends Example {
  override protected lazy val component: Component = Container(
    new Label("Hello, [#00ff00ff]World![]\n\nTesting, this is some [#00ffffff]really long text[] that should wrap multiple lines!") with PointerSupport {
      font @= Fonts.Pacifico.normal
      center @= screen.center
      middle @= screen.middle
      wrap @= true
      width @= 1000.0

      color := (if (pointer.isOver) Color.Red else Color.White)

      override def toString: String = "text"
    },
    new Label("Giant Text Here") {
      font @= Fonts.Pacifico.large
      center := screen.center
      top @= 10.0

      color @= Color.White
    }
  )
}