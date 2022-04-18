package examples

import org.sgine.component.{Children, Component, Container, DimensionedComponent, TextureView}
import org.sgine.render.RenderContext
import org.sgine.{Screen, UI}

object HelloSgine extends Screen { screen =>
  private lazy val logo = new TextureView("basketball.png") {
    center @= screen.center
    middle @= screen.middle
  }

  private lazy val container = new Container with DimensionedComponent { self =>
    x @= 500.0
    y @= 500.0

    override lazy val children: Children[Component] = Children(self, List(logo))
  }

  override protected def root: Component = container

  def main(args: Array[String]): Unit = {
    UI.run {
      UI.fpsFont @= OpenSans.Regular.normal
      UI.drawFPS @= true
      UI.screen @= this
    }
  }
}