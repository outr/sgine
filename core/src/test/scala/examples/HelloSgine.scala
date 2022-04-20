package examples

import org.sgine.component.{Component, InteractiveComponent, Image}
import reactify._

object HelloSgine extends Example {
  override protected lazy val root: Component = new Image("sgine.png") with InteractiveComponent {
    center @= screen.center
    middle @= screen.middle
  }
}