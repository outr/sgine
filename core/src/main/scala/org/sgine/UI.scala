package org.sgine

import com.badlogic.gdx
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.BitmapFont
import org.sgine.audio.Audio
import org.sgine.task.TaskSupport
import reactify._

object UI extends gdx.Screen with TaskSupport { ui =>
  private var _delta: Double = 0.0

  def delta: Double = _delta

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
  val screens = new ScreenManager
  val render: Channel[Double] = Channel[Double]

  private var disposed = false
  private var init: () => Unit = () => ()

  private lazy val inputProcessor = new UIInputProcessor

  def run(init: => Unit): gdx.Game = {
    this.init = () => init
    overCheckFrameRate().foreach { frameRate =>
      val delay = 1.0 / frameRate.toDouble
      ui.updates.every(delay) {
        inputProcessor.checkOver()
      }
    }
    updates.attach(Audio.update)
    game
  }

  private object game extends gdx.Game {
    override def create(): Unit = {
      screens.adding.attach { screen =>
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

    _delta = delta.toDouble
    ui.update(_delta)
    screens.update(_delta)

    render @= _delta
    val list = screens()
    list.foreach(_.renderScreen(_delta))
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

  final def quit(): Unit = Gdx.app.exit()
}