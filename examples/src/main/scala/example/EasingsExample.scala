package example

import org.sgine._
import org.sgine.lwjgl.BasicDesktopApp
import org.sgine.screen.FPSLoggingSupport
import org.sgine.transition.easing.Easing
import org.sgine.widget.Image

object EasingsExample extends BasicDesktopApp with FPSLoggingSupport {
  create.on {
    val image = new Image("sgine.png") {
      position.center := ui.center
      position.middle := ui.middle
    }

    forever(image.position.x transitionTo 0.0 in 1.seconds easing Easing.BounceOut
      andThen (image.position.right transitionTo ui.width.get in 1.seconds easing Easing.SineIn)
      andThen (image.position.center transitionTo ui.width.get / 2.0 in 1.seconds easing Easing.ElasticOut)
      andThen (image.rotation transitionTo -360.0 in 2.seconds easing Easing.ElasticOut)
      andThen function(image.rotation := 0.0)).start(ui)
    add(image)
  }
}