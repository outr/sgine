package example

import org.sgine.component.{Component, KeyFrameAnimationView}
import org.sgine.drawable.Texture
import perfolation._
import reactify._

object KeyFrameAnimationExample extends Example {
  private lazy val textures: Vector[Texture] = (0 to 19).toVector.map { index =>
    Texture.internal(s"air-minor/air-minor_${index.f(i = 2, f = 0)}.png", scaleX = 3.0, scaleY = 3.0)
  }

  override protected lazy val component: Component = new KeyFrameAnimationView(textures) {
    center @= screen.center
    middle @= screen.middle
  }
}