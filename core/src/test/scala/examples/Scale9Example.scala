package examples
import org.sgine.component.{Component, Container, Scale9View}
import org.sgine.texture.{Scale9, Texture}

object Scale9Example extends Example {
  private lazy val scale9 = Scale9(Texture.internal("scale9test.png"), 50, 50, 50, 50)

  override protected lazy val root: Component = Container(
    new Scale9View(scale9) {
      center @= screen.center
      middle @= screen.middle
      width @= 2000.0
      height @= 1200.0
    },
    new Scale9View(scale9) {
      center @= screen.center
      middle @= screen.middle
      width @= 1000.0
      height @= 800.0
    },
    new Scale9View(scale9) {
      center @= screen.center
      middle @= screen.middle
      width @= 400.0
      height @= 400.0
    }
  )
}
