package example

import org.sgine._
import org.sgine.lwjgl.BasicDesktopApp
import org.sgine.widget.Image

object ColorsExample extends BasicDesktopApp {
  create.on {
    val image = new Image("sgine.png") {
      position.center := ui.center
      position.middle := ui.middle
    }

    render.every(1.seconds, runNow = true) {
      (image.color.red transitionTo math.random from image.color.red.get in 1.seconds
        and (image.color.green transitionTo math.random from image.color.green.get in 1.seconds)
        and (image.color.blue transitionTo math.random from image.color.blue.get in 1.seconds)
      ).start(ui)
    }
    add(image)
  }
}