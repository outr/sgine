package org.sgine.jglfw

import com.badlogic.gdx.Application
import com.badlogic.gdx.backends.jglfw.{JglfwApplication, JglfwApplicationConfiguration}
import org.sgine.{Platform, UI}

trait JGLFWPlatform extends Platform[JglfwApplicationConfiguration] {
  override protected def createConfig(): JglfwApplicationConfiguration = new JglfwApplicationConfiguration

  override protected def create(ui: UI, config: JglfwApplicationConfiguration): Application = {
    new JglfwApplication(ui.listener, config)
  }
}