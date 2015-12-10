package org.sgine.lwjgl

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import org.sgine.{BasicUI, Screen, UI}

class BasicDesktopApp extends Screen {
  private val basicUI = new LWJGLPlatform with BasicUI {
    override def screen: Screen = BasicDesktopApp.this

    override protected def createUI(): UI = this

    override def init(config: LwjglApplicationConfiguration): Unit = {
      config.title = BasicDesktopApp.this.getClass.getSimpleName.replaceAll("[$]", "")
      config.width = 1024
      config.height = 768
      config.forceExit = true
      config.samples = 8
      config.vSyncEnabled = false
      config.foregroundFPS = 0
    }
  }

  def main(args: Array[String]): Unit = {
    basicUI.main(args)
  }
}
