package org.sgine

import com.badlogic.gdx
import com.badlogic.gdx.backends.lwjgl3.{Lwjgl3Application, Lwjgl3ApplicationConfiguration}
import org.sgine.render.RenderContext
import reactify.Var

class UI(initialScreen: Screen) { ui =>
  val title: Var[String] = Var("Sgine")
  val screen: Var[Screen] = Var(initialScreen)

  protected def init(): Unit = {}

  def run(): Unit = {
    val config = new Lwjgl3ApplicationConfiguration
    title.attachAndFire { title =>
      config.setTitle(title)
    }
    config.setWindowedMode(1920, 1080)
    new Lwjgl3Application(game, config)
  }

  private object game extends gdx.Game {
    override def create(): Unit = {
      RenderContext.init()
      ui.init()
      ui.screen.attachAndFire { s =>
        setScreen(s.screen)
      }
    }

    override def dispose(): Unit = {
      RenderContext.dispose()
    }
  }
}