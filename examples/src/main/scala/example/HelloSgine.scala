package example

import com.badlogic.gdx.backends.jglfw.JglfwApplicationConfiguration
import org.sgine._
import org.sgine.jglfw.JGLFWPlatform
import org.sgine.render.{ClearScreen, SingleScreen}
import org.sgine.widget.Image
import pl.metastack.metarx._

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
  create.on {
    this += new Image("sgine.png") {    // Top-Left
      position.x := 50.0
      position.y := ui.height - size.height - 50.0
    }
    this += new Image("sgine.png") {    // Top-Right
      position.x := ui.width - size.width - 50.0
      position.y := ui.height - size.height - 50.0
    }
    this += new Image("sgine.png") {    // Bottom-Left
      position.x := 50.0
      position.y := 50.0
    }
    this += new Image("sgine.png") {    // Bottom-Right
      position.x := ui.width - size.width - 50.0
      position.y := 50.0
    }
    this += new Image("sgine.png") {    // Center
      val aspect = size.width.get / size.height.get
      position.x := (ui.width / 2.0) - (size.width / 2.0)
      position.y := (ui.height / 2.0) - (size.height / 2.0)
      size.width := ui.width / 2.0
      size.height := size.width / aspect
    }
  }
}