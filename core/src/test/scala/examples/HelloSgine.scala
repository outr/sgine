package examples

import org.sgine.component.{Children, Component, Container, DimensionedComponent, InteractiveComponent, TextureView}
import org.sgine.texture.Texture
import org.sgine.{Color, Screen, UI}

object HelloSgine extends Screen { screen =>
//  private lazy val background = new TiledTextureComponent(Texture.internal("background_desert.png"), overflow = true) {
//    override type T = TiledComponent
//
//    width @= 256.0 * 13
//    height @= 2160.0
//
//    override protected def create(tileX: Int, tileY: Int): TiledComponent = new TiledComponent(this, tileX, tileY)
//  }
  private lazy val logo = new TextureView("background_desert.png") with InteractiveComponent {
//    center @= screen.center
//    middle @= screen.middle

    left @= 100.0
    top @= 100.0
    scaleX @= 2.0
    scaleY @= 2.0
    rotation @= 45.0

    override def toString: String = "logo"
  }
  private lazy val logo2 = new TextureView("background_desert.png") with InteractiveComponent {
    //    center @= screen.center
    //    middle @= screen.middle

    left @= 200.0
    top @= 200.0
    scaleX @= 2.0
    scaleY @= 2.0
    rotation @= 45.0

    pointer.down.attach { evt =>
      scribe.info(s"Logo 2: $evt")
    }
    color := {
      if (pointer.over) {
        Color.Red
      } else {
        Color.White
      }
    }

    override def toString: String = "logo2"
  }

  private lazy val container = new Container with DimensionedComponent {
    x @= 200.0
    y @= 200.0

    override lazy val children: Children[Component] = Children(this, List(logo, logo2))
  }

  override protected lazy val root: Component = container //Container(logo, logo2) //Container(background, logo)

  def main(args: Array[String]): Unit = UI.run {
    UI.fpsFont @= OpenSans.Regular.normal
    UI.drawFPS @= true
    UI.screen @= this
  }
}