package org.sgine.lwjgl

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import org.sgine.{BasicUI, Screen, UI}

class BasicDesktopApp extends Screen {
  def init(config: Lwjgl3ApplicationConfiguration): Unit = {
    config.setWindowedMode(1024, 768)
//    config.forceExit = true
    config.useVsync(true)
    config.setBackBufferConfig(8, 8, 8, 8, 16, 0, 8)
  }

  private val basicUI = new LWJGLPlatform with BasicUI {
    override def screen: Screen = BasicDesktopApp.this

    override protected def createUI(): UI = this

    override def init(config: Lwjgl3ApplicationConfiguration): Unit = BasicDesktopApp.this.init(config)
  }
  create.on {
    if (basicUI.title.get == "") {
      basicUI.title := BasicDesktopApp.this.getClass.getSimpleName.replaceAll("[$]", "")
    }
  }

  def main(args: Array[String]): Unit = {
    basicUI.main(args)
  }
}
