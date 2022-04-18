package examples

import org.sgine.component.{Component, TextureView}
import org.sgine.render.RenderContext
import org.sgine.{Screen, UI}

object HelloSgine extends Screen { screen =>
  private lazy val logo = new TextureView("basketball.png") {
    center @= screen.center
    middle @= screen.middle
  }

  override protected def root: Component = logo

  def main(args: Array[String]): Unit = {
    UI.run {
      UI.fpsFont @= OpenSans.Regular.normal
      UI.drawFPS @= true
      UI.screen @= this
    }
  }
}