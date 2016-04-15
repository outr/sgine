package example

import org.powerscala.Color
import org.sgine._
import org.sgine.lwjgl.BasicDesktopApp
import org.sgine.widget.Animation

object AnimationExample extends BasicDesktopApp {
  create.once {
    this += new Animation {
      color := Color.Red
      position.center := ui.center
      position.middle := ui.middle
      frameDuration := 0.1

      framesFromTexture("loading.png", 72, 72)
    }
  }
}
