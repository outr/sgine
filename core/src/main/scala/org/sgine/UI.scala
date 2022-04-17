package org.sgine

import com.badlogic.gdx
import com.badlogic.gdx.backends.lwjgl3.{Lwjgl3Application, Lwjgl3ApplicationConfiguration}
import com.badlogic.gdx.graphics.g2d.BitmapFont
import org.sgine.render.RenderContext
import reactify.Var

object UI { ui =>
  val title: Var[String] = Var("Sgine")
  val screen: Var[Screen] = Var(Screen.Blank)

  lazy val drawFPS: Var[Boolean] = Var(false)
  lazy val fpsFont: Var[BitmapFont] = Var(RenderContext.fontNormal)

  protected def init(): Unit = {}

  def run(): Unit = {
    val config = new Lwjgl3ApplicationConfiguration
    title.attachAndFire { title =>
      config.setTitle(title)
    }
    config.useVsync(true)
    config.setForegroundFPS(0)
    config.setIdleFPS(0)
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