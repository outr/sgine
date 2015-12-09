package example

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import org.sgine._
import org.sgine.lwjgl.LWJGLPlatform
import org.sgine.widget.Image

object ColorsExample extends LWJGLPlatform with BasicUI {
  override lazy val screen: Screen = new ColorsScreen

  override def init(config: LwjglApplicationConfiguration): Unit = {}

  override protected def createUI(): UI = this
}

class ColorsScreen extends Screen {
  create.on {
    val image = new Image("sgine.png") {
      position.x := (ui.width / 2.0) - (size.width / 2.0)
      position.y := (ui.height / 2.0) - (size.height / 2.0)
//      render.every(0.4, runNow = true, stopIn = 10.0) {
//        color.red := math.random
//        color.green := math.random
//        color.blue := math.random
//      }
      color.alpha := 0.0
//      render.until(color.alpha.get >= 1.0) {
//        color.alpha := math.min(color.alpha.get + 0.01, 1.0)
//      }
      // color.alpha.transitionTo 1.0 from 0.0 in 5.seconds
    }
    image.color.alpha transitionTo 1.0 from 0.0 in 5.seconds start()
    add(image)
  }
}