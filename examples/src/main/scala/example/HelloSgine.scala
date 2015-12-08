package example

import com.badlogic.gdx.backends.jglfw.JglfwApplicationConfiguration
import org.sgine._
import org.sgine.jglfw.JGLFWPlatform
import org.sgine.render.{ClearScreen, SingleScreen}
import org.sgine.widget.Image

object HelloSgine extends JGLFWPlatform with UI with ClearScreen with SingleScreen {
  lazy val screen = new HelloScreen

  override protected def createUI(): UI = this

  override def init(config: JglfwApplicationConfiguration): Unit = {
    config.title = "Hello Sgine"
    config.width = 1024
    config.height = 768
    config.forceExit = true
    config.samples = 8
    config.vSync = false
  }
}

class HelloScreen extends Screen {
  lazy val image = Image("sgine.png")

  create.on {
    add(image)
  }
}