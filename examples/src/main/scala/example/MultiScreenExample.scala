package example

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import org.sgine._
import org.sgine.lwjgl.DesktopApp
import org.sgine.screen.{TransitionSupport, VirtualMode, VirtualSizeSupport}
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

  override def init(config: LwjglApplicationConfiguration): Unit = {
    super.init(config)

    config.vSyncEnabled = true
  }
}

class SimpleScreen(c: Color) extends Screen with VirtualSizeSupport with TransitionSupport {
  override val virtualWidth: Double = 1024.0
  override val virtualHeight: Double = 768.0
  override val virtualMode: VirtualMode = VirtualMode.Stretch

  create.on {
    this += new Image("1024.jpg") {
      position.x := 0.0.vx
      position.y := 0.0.vy
      size.width := 1024.0.vw
      size.height := 768.0.vh
      color := c
    }
    this += new Image("sgine.png") {
      position.center := ui.width / 2.0
      position.middle := ui.height / 2.0
    }
  }

  def transition() = {
    val fadeIn = color.alpha transitionTo 1.0 in 1.seconds
    val s1tos2 = MultiScreenExample.screen2.transitions.fade.cross(this, 1.seconds)
    val s2tos3 = MultiScreenExample.screen3.transitions.slideOver.left(MultiScreenExample.screen2, 2.seconds, Easing.BounceOut)
    fadeIn andThen s1tos2 andThen s2tos3 start()
  }
}