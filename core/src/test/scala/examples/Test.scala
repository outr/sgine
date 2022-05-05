package examples

import com.badlogic.gdx.backends.lwjgl3.{Lwjgl3Application, Lwjgl3ApplicationConfiguration}
import com.badlogic.gdx.graphics.{GL20, Texture}
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.{Game, Gdx, Screen}

object Test extends Game with Screen {
  private lazy val stage = new Stage(new FitViewport(3840.0f, 2160.0f))
  private lazy val image = new Image(new Texture(Gdx.files.internal("crate.jpg"), true))

  override def create(): Unit = {
    stage.addActor(image)
    setScreen(this)
  }

  override def show(): Unit = {}

  override def render(delta: Float): Unit = {
    Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    stage.act(delta)
    stage.draw()
  }

  override def hide(): Unit = {}

  override def resize(width: Int, height: Int): Unit = {
    stage.getViewport.update(width, height, true)
  }

  def main(args: Array[String]): Unit = {
    val config = new Lwjgl3ApplicationConfiguration
    config.useVsync(true)
    config.setForegroundFPS(0)
    config.setIdleFPS(0)
    config.setWindowedMode(1920, 1080)
    config.setBackBufferConfig(8, 8, 8, 8, 16, 2, 2)
    new Lwjgl3Application(this, config)
  }
}