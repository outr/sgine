package examples

import org.sgine.component.{Component, InteractiveComponent, TextureView}

object HelloSgine extends Example {
  override protected lazy val root: Component = new TextureView("sgine.png") with InteractiveComponent {
    center @= screen.center
    middle @= screen.middle
  }
}