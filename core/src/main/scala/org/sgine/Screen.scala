package org.sgine

import com.badlogic.gdx
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.{Camera, GL20, OrthographicCamera}
import org.sgine.component.Component
import org.sgine.render.{RenderContext, Renderable}
import org.sgine.update.Updatable
import reactify.{Val, Var}

trait Screen extends Renderable with Updatable { self =>
  lazy val flatChildren: Val[List[Component]] = Val(Component.flatChildren(root))
  lazy val renderables: Val[List[Renderable]] = Val(flatChildren.collect {
    case r: Renderable => r
  }.filter(_.shouldRender).sorted(Renderable.ordering))
  lazy val updatables: Val[List[Updatable]] = Val(flatChildren.collect {
    case u: Updatable => u
  })

  val width: Var[Double] = Var(3840.0)
  val height: Var[Double] = Var(2160.0)

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
      }
    }

    override def hide(): Unit = {
      Gdx.input.setInputProcessor(null)
    }
  }
}