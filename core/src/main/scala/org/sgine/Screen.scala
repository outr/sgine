package org.sgine

import com.badlogic.gdx
import com.badlogic.gdx.{Gdx, InputProcessor}
import com.badlogic.gdx.graphics.{Camera, Color, GL20, OrthographicCamera}
import org.sgine.component.{Children, Component, Container, FPSView, TextView, TypedContainer}
import org.sgine.render.{RenderContext, Renderable}
import org.sgine.update.Updatable
import reactify._

trait Screen extends Renderable with Updatable with Container { self =>
  lazy val flatChildren: Val[List[Component]] = Val(TypedContainer.flatChildren(children: _*))
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

  object fpsView extends FPSView {
    visible := UI.drawFPS
    top @= 0.0
    right := self.width - 10.0
  }

  override lazy val children: Children[Component] = Children(this, List(root, fpsView))

  override def render(context: RenderContext): Unit = renderables().foreach(_.render(context))

  override def update(delta: Double): Unit = updatables().foreach(_.update(delta))

  private[sgine] object screen extends gdx.ScreenAdapter {
    override def show(): Unit = {
      Gdx.input.setInputProcessor(inputProcessor)
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

  private object inputProcessor extends InputProcessor {
    override def keyDown(keycode: Int): Boolean = true

    override def keyUp(keycode: Int): Boolean = true

    override def keyTyped(character: Char): Boolean = true

    override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = true

    override def touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = true

    override def touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = true

    override def mouseMoved(screenX: Int, screenY: Int): Boolean = {
//      scribe.info(s"Pointer: $screenX x $screenY")
      true
    }

    override def scrolled(amountX: Float, amountY: Float): Boolean = true
  }
}

object Screen {
  case object Blank extends Screen {
    override protected lazy val root: Component = new Component {
    }
  }
}