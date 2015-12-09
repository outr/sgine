package org.sgine.lwjgl

import com.badlogic.gdx.Application
import com.badlogic.gdx.backends.lwjgl.{LwjglApplication, LwjglApplicationConfiguration}
import org.sgine.{Platform, UI}

trait LWJGLPlatform extends Platform[LwjglApplicationConfiguration] {
  override protected def createConfig(): LwjglApplicationConfiguration = new LwjglApplicationConfiguration

  override protected def create(ui: UI, config: LwjglApplicationConfiguration): Application = {
    new LwjglApplication(ui.listener, config)
  }
}
