package org.sgine

import com.badlogic.gdx
import com.badlogic.gdx.backends.lwjgl3.{Lwjgl3Application, Lwjgl3ApplicationConfiguration}
import com.badlogic.gdx.graphics.g2d.BitmapFont
import org.sgine.render.RenderContext
import reactify._

object UI { ui =>
  val title: Var[String] = Var("Sgine")
  val screen: Var[Screen] = Var(Screen.Blank)
  val updateFPS: Var[Int] = Var(60)

  lazy val drawFPS: Var[Boolean] = Var(false)
  lazy val fpsFont: Var[BitmapFont] = Var(RenderContext.fontNormal)

  private var disposed = false
  private var init: () => Unit = () => ()

  // Background thread for updates
  private lazy val updateThread = new Thread {
    setDaemon(true)

    private var lastUpdate = System.currentTimeMillis()

    override def run(): Unit = while(!disposed) {
      val delay = (1000.0 / updateFPS.toDouble).toLong
      Thread.sleep(delay)

      val now = System.currentTimeMillis()
      val delta = (now - lastUpdate) / 1000.0
      screen().update(delta)
      lastUpdate = now
    }
  }

  def run(init: => Unit): Unit = {
    this.init = () => init
    val config = new Lwjgl3ApplicationConfiguration
    title.attachAndFire { title =>
      config.setTitle(title)
    }
    config.useVsync(true)
    config.setForegroundFPS(0)
    config.setIdleFPS(0)
    config.setWindowedMode(1920, 1080)
    config.setBackBufferConfig(8, 8, 8, 8, 16, 2, 2)
    new Lwjgl3Application(game, config)
  }

  private object game extends gdx.Game {
    override def create(): Unit = {
      RenderContext.init()
      ui.init()
      ui.screen.attachAndFire { s =>
        setScreen(s.screenAdapter)
      }
      updateThread.start()
    }

    override def dispose(): Unit = {
      disposed = true
      RenderContext.dispose()
    }
  }
}