package org.sgine

import com.badlogic.gdx
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.backends.lwjgl3.{Lwjgl3Application, Lwjgl3ApplicationConfiguration}
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.BitmapFont
import org.sgine.audio.Audio
import org.sgine.task.TaskSupport
import reactify._

object UI extends gdx.Screen with TaskSupport { ui =>
  lazy val fontNormal: BitmapFont = {
    val f = new BitmapFont
    f.getData.setScale(4.0f)
    f
  }
  lazy val fontSmall: BitmapFont = {
    val f = new BitmapFont
    f.getData.setScale(3.0f)
    f
  }

  val title: Var[String] = Var("Sgine")

  /**
    * Must be set prior to starting the UI. Defaults to Some(60).
    */
  val overCheckFrameRate: Var[Option[Int]] = Var(Some(60))
  val screens = new Screens
  object screen {
    def :=(screen: => Screen): Unit = apply(screen)

    def @=(screen: Screen): Unit = apply(screen)

    def apply(screen: => Screen): Unit = screens.set(Vector(screen))
  }
  val render: Channel[Double] = Channel[Double]

  private var disposed = false
  private var init: () => Unit = () => ()

  private lazy val inputProcessor = new UIInputProcessor

  def run(init: => Unit): Unit = {
    this.init = () => init
    val config = new Lwjgl3ApplicationConfiguration
    title.attachAndFire { title =>
      Option(Gdx.graphics) match {
        case Some(g) => g.setTitle(title)
        case None => config.setTitle(title)
      }
    }
    config.useVsync(true)
    config.setForegroundFPS(0)
    config.setIdleFPS(0)
    config.setWindowedMode(1920, 1080)
    config.setBackBufferConfig(8, 8, 8, 8, 16, 2, 2)
    overCheckFrameRate().foreach { frameRate =>
      val delay = 1.0 / frameRate.toDouble
      ui.updates.every(delay) {
        inputProcessor.checkOver()
      }
    }
    updates.attach(Audio.update)
    new Lwjgl3Application(game, config)
  }

  private object game extends gdx.Game {
    override def create(): Unit = {
      screens.added.attach { screen =>
        ui.render.on {
          screen.verifyInit()
          screen.stage.getViewport.update(Gdx.graphics.getWidth, Gdx.graphics.getHeight, true)
        }
      }
      ui.init()
      setScreen(ui)
    }

    override def dispose(): Unit = {
      disposed = true
    }
  }

  override def show(): Unit = {
    Gdx.input.setInputProcessor(inputProcessor)
  }

  override def render(delta: Float): Unit = {
    Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    val d = delta.toDouble
    ui.update(d)
    screens.update(d)

    render @= d
    val list = screens()
    list.foreach(_.renderScreen(d))
  }

  override def resize(width: Int, height: Int): Unit = {
    val list = screens()
    list.foreach(_.stage.getViewport.update(width, height, true))
  }

  override def pause(): Unit = {}

  override def resume(): Unit = {}

  override def hide(): Unit = {
    Gdx.input.setInputProcessor(null)
  }

  override def dispose(): Unit = {}
}