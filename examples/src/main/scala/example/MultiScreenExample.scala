package example

import org.powerscala.Color
import org.sgine._
import org.sgine.lwjgl.DesktopApp
import org.sgine.screen.FPSLoggingSupport
import org.sgine.transition.Transition
import org.sgine.transition.easing.Easing
import org.sgine.widget.Image

object MultiScreenExample extends DesktopApp {
  lazy val screen1 = new SimpleScreen(Color.White)
  lazy val screen2 = new SimpleScreen(Color.Red)
  lazy val screen3 = new SimpleScreen(Color.Yellow)

  create.on {
    activeScreens.add(screen1)
    screen1.transition()
  }
}

class SimpleScreen(c: Color) extends Screen with VirtualSizeSupport with FPSLoggingSupport {
  virtualMode := VirtualMode.Stretch

  create.on {
    this += new Image("1024.jpg") {
      position.x := 0.0.vx
      position.y := 0.0.vy
      size.width := 1024.0.vw
      size.height := 768.0.vh
      color := c
    }
    this += new Image("sgine.png") {
      position.center := ui.center
      position.middle := ui.middle
    }
  }

  def transition() = {
    import MultiScreenExample._
    val fadeIn = color.alpha transitionTo 1.0 in 1.seconds
    val s1tos2 = Transition.screen.fade.cross(screen1, screen2, 1.seconds)
    val s2tos3 = Transition.screen.slideOver.left(screen2, screen3, 2.seconds, Easing.BounceOut)
    fadeIn andThen s1tos2 andThen s2tos3 start ui
  }
}