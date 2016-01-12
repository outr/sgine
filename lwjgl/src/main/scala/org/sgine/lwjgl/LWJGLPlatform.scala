package org.sgine.lwjgl

import com.badlogic.gdx.Application
import com.badlogic.gdx.backends.lwjgl3.{Lwjgl3Application, Lwjgl3ApplicationConfiguration}
import org.sgine.{Platform, UI}

trait LWJGLPlatform extends Platform[Lwjgl3ApplicationConfiguration] {
  override protected def createConfig(): Lwjgl3ApplicationConfiguration = new Lwjgl3ApplicationConfiguration

  override protected def create(ui: UI, config: Lwjgl3ApplicationConfiguration): Application = {
    new Lwjgl3Application(ui.listener, config)
  }
}
