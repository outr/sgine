package org.sgine

import com.badlogic.gdx
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.{Camera, Color, GL20, OrthographicCamera}
import org.sgine.component.{Component, TypedContainer}
import org.sgine.render.{RenderContext, Renderable}
import org.sgine.update.Updatable
import reactify._

trait Screen extends Renderable with Updatable { self =>
  lazy val flatChildren: Val[List[Component]] = Val(TypedContainer.flatChildren(root))
  lazy val renderables: Val[List[Renderable]] = Val(flatChildren.collect {
    case r: Renderable => r
  }.filter(_.shouldRender).sorted(Renderable.ordering))
  lazy val updatables: Val[List[Updatable]] = Val(flatChildren.collect {
    case u: Updatable => u
  })

  val width: Var[Double] = Var(3840.0)
  val height: Var[Double] = Var(2160.0)

  val center: Val[Double] = Val(width / 2.0)
  val middle: Val[Double] = Val(height / 2.0)

  protected[sgine] lazy val camera: Camera = {
    val c = new OrthographicCamera
    c.setToOrtho(false, width().toFloat, height().toFloat)
    c.update()
    c
  }

  private lazy val _context = new RenderContext(this)

  protected def root: Component

  override def render(context: RenderContext): Unit = renderables().foreach(_.render(context))

  override def update(delta: Double): Unit = updatables().foreach(_.update(delta))

  private[sgine] object screen extends gdx.ScreenAdapter {
    override def show(): Unit = {
//      Gdx.input.setInputProcessor(inputProcessor)
    }

    override def render(delta: Float): Unit = {
      Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

      _context.renderWith {
        self.render(_context)

        if (UI.drawFPS) _context.draw(
          text = s"${Gdx.graphics.getFramesPerSecond}fps",
          x = width - 20.0,
          y = 20.0,
          alignment = Alignment.Right,
          color = Color.WHITE,
          font = UI.fpsFont
        )
      }
    }

    override def hide(): Unit = {
      Gdx.input.setInputProcessor(null)
    }
  }
}

object Screen {
  case object Blank extends Screen {
    override protected lazy val root: Component = new Component {
    }
  }
}