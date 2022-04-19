package examples

import org.sgine.component.{AnimationView, Component}
import org.sgine.texture.Texture
import perfolation._

object AnimationExample extends Example {
  private lazy val textures: Vector[Texture] = (0 to 19).toVector.map { index =>
    Texture.internal(s"air-minor/air-minor_${index.f(i = 2, f = 0)}.png", scaleX = 3.0, scaleY = 3.0)
  }

  override protected lazy val root: Component = new AnimationView(textures) {
    center @= screen.center
    middle @= screen.middle
  }
}