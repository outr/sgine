package example

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import org.sgine._
import org.sgine.lwjgl.LWJGLPlatform
import org.sgine.widget.Image

object TransitionsExample extends LWJGLPlatform with BasicUI {
  override lazy val screen: Screen = new TransitionsScreen

  override def init(config: LwjglApplicationConfiguration): Unit = {}

  override protected def createUI(): UI = this
}

class TransitionsScreen extends Screen {
  create.on {
    val image = new Image("sgine.png") {
      position.x := (ui.width / 2.0) - (size.width / 2.0)
      position.y := (ui.height / 2.0) - (size.height / 2.0)
    }
    val transition = (
      image.color.alpha transitionTo 1.0 from 0.0 in 5.seconds
        andThen(
          image.position.x transitionTo 0.0 from image.position.x.get in 1.seconds
          and(image.position.y transitionTo 0.0 from image.position.y.get in 1.seconds)
        )
        andThen(image.color.red transitionTo 0.0 from 1.0 in 2.seconds)
        andThen(image.color.green transitionTo 0.0 from 1.0 in 2.seconds)
        andThen(image.color.blue transitionTo 0.0 from 1.0 in 2.seconds)
        andThen(
        image.color.red transitionTo 1.0 from 0.0 in 1.seconds
          and(image.color.green transitionTo 1.0 from 0.0 in 1.seconds)
          and(image.color.blue transitionTo 1.0 from 0.0 in 1.seconds)
        )
        andThen function {
          println("Finished!")
        }
      )
    transition.start()
    add(image)
  }
}