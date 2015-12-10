package example

import com.badlogic.gdx.graphics.FPSLogger
import org.sgine._
import org.sgine.lwjgl.BasicDesktopApp
import org.sgine.transition.easing.Easing
import org.sgine.widget.Image

object EasingsExample extends BasicDesktopApp {
  val fps = new FPSLogger

  create.on {
    val image = new Image("sgine.png") {
      position.x := (ui.width / 2.0) - (size.width / 2.0)
      position.y := (ui.height / 2.0) - (size.height / 2.0)
    }

    forever(image.position.x transitionTo 0.0 in 1.seconds easing Easing.BounceOut
      andThen (image.position.right transitionTo ui.width.get in 1.seconds easing Easing.SineIn)
      andThen (image.position.center transitionTo ui.width.get / 2.0 in 1.seconds easing Easing.ElasticOut)
      andThen (image.rotation transitionTo -360.0 in 2.seconds easing Easing.ElasticOut)
      andThen function(image.rotation := 0.0)).start()
    add(image)
  }
  render.on {
    fps.log()
  }
}