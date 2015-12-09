package example

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.badlogic.gdx.graphics.FPSLogger
import org.sgine._
import org.sgine.lwjgl.LWJGLPlatform
import org.sgine.widget.Image
import pl.metastack.metarx._

object HelloSgine extends LWJGLPlatform with BasicUI {
  lazy val screen = new HelloScreen

  override protected def createUI(): UI = this

  override def init(config: LwjglApplicationConfiguration): Unit = {
    config.title = "Hello Sgine"
    config.width = 1024
    config.height = 768
    config.forceExit = true
    config.samples = 8
    config.vSyncEnabled = false
    config.foregroundFPS = 0
  }
}

class HelloScreen extends Screen {
  val fps = new FPSLogger

  ui.continuousRendering := false

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
  render.on {
    fps.log()
  }
}