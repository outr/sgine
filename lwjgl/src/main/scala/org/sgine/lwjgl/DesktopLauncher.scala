package org.sgine.lwjgl

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.backends.lwjgl3.{Lwjgl3Application, Lwjgl3ApplicationConfiguration}
import org.sgine.{SgineApp, UI}

trait DesktopLauncher {
  protected def createConfig(): Lwjgl3ApplicationConfiguration = {
    val config = new Lwjgl3ApplicationConfiguration
    config.useVsync(true)
    config.setForegroundFPS(0)
    config.setIdleFPS(0)
    config.setWindowedMode(1920, 1080)
    config.setBackBufferConfig(8, 8, 8, 8, 16, 2, 2)
    config
  }

  protected def app: SgineApp

  def main(args: Array[String]): Unit = {
    val config = createConfig()
    UI.title.attachAndFire { title =>
      Option(Gdx.graphics) match {
        case Some(g) => g.setTitle(title)
        case None => config.setTitle(title)
      }
    }
    val game = UI.run {
      app.init()
    }
    new Lwjgl3Application(game, config)
  }
}