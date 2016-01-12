package org.sgine.lwjgl

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import org.sgine.{StandardUI, UI}

class DesktopApp extends LWJGLPlatform with StandardUI {
  override protected def createUI(): UI = this

  override def init(config: Lwjgl3ApplicationConfiguration): Unit = {
    config.setWindowedMode(1024, 768)
    //    config.forceExit = true
    config.useVsync(true)
    config.setBackbufferConfig(8, 8, 8, 8, 16, 0, 8)
  }

  create.on {
    if (title.get == "") {
      title := getClass.getSimpleName.replaceAll("[$]", "")
    }
  }
}