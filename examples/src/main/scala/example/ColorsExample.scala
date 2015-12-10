package example

import org.sgine._
import org.sgine.lwjgl.BasicDesktopApp
import org.sgine.widget.Image

object ColorsExample extends BasicDesktopApp {
  create.on {
    val image = new Image("sgine.png") {
      position.x := (ui.width / 2.0) - (size.width / 2.0)
      position.y := (ui.height / 2.0) - (size.height / 2.0)
    }

    render.every(1.seconds, runNow = true) {
      (image.color.red transitionTo math.random from image.color.red.get in 1.seconds
        and (image.color.green transitionTo math.random from image.color.green.get in 1.seconds)
        and (image.color.blue transitionTo math.random from image.color.blue.get in 1.seconds)
      ).start()
    }
    add(image)
  }
}