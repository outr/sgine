package org.sgine.lwjgl

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import org.sgine.{StandardUI, UI}

class DesktopApp extends LWJGLPlatform with StandardUI {
  override protected def createUI(): UI = this

  override def init(config: LwjglApplicationConfiguration): Unit = {
    config.title = getClass.getSimpleName.replaceAll("[$]", "")
    config.width = 1024
    config.height = 768
    config.forceExit = true
    config.samples = 8
    config.vSyncEnabled = false
    config.foregroundFPS = 0
  }
}