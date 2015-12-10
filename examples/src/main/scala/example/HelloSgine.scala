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

  ui.continuousRendering := true

  create.on {
    this += new Image("sgine.png") {    // Top-Left
      position.left := 50.0
      position.top := ui.height - 50.0
    }
    this += new Image("sgine.png") {    // Top-Right
      position.right := ui.width - 50.0
      position.top := ui.height - 50.0
    }
    this += new Image("sgine.png") {    // Bottom-Left
      position.left := 50.0
      position.bottom := 50.0
    }
    this += new Image("sgine.png") {    // Bottom-Right
      position.right := ui.width - 50.0
      position.bottom := 50.0
    }
    this += new Image("sgine.png") {    // Center
      val aspect = size.width.get / size.height.get
      position.center := ui.width / 2.0
      position.middle := ui.height / 2.0
      size.width := ui.width / 2.0
      size.height := size.width / aspect
    }
  }
  render.on {
    fps.log()
  }
}