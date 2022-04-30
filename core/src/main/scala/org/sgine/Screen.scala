package org.sgine

import com.badlogic.gdx
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.{Camera, GL20, OrthographicCamera}
import com.badlogic.gdx.scenes.scene2d.{Group, Stage}
import com.badlogic.gdx.utils.viewport.{FitViewport, ScreenViewport}
import org.sgine.component.{Children, Component, FPSView, InteractiveComponent, TypedContainer}
import org.sgine.event.key.{KeyChannel, KeyEvent, KeyInput}
import org.sgine.event.{InputProcessor, TypedEvent}
import org.sgine.event.pointer.PointerEvents
import org.sgine.render.{RenderContext, Renderable}
import org.sgine.update.Updatable
import reactify._

trait Screen extends Renderable with Updatable with TypedContainer[Component] with InteractiveComponent { self =>
  /**
   * The `Component` at the current cursor position. If nothing else is at the cursor position the `Screen` will be
   * returned.
   */
  val atCursor: Var[InteractiveComponent] = Var[InteractiveComponent](self)

  width @= 3840.0
  height @= 2160.0

  lazy val stage = new Stage()

  width.and(height).on(screenSizeChanged())

  override lazy val actor: Group = {
    screenSizeChanged()
    stage.getRoot
  }

  override lazy val pointer: ScreenPointerEvents = new ScreenPointerEvents

  lazy val input = new KeyInput

  private lazy val _context = new RenderContext(this)

  protected def root: Component

  object fpsView extends FPSView {
    visible := UI.drawFPS
    top @= 0.0
    right := self.width - 10.0
  }

  override lazy val children: Children[Component] = Children(this, Vector(root, fpsView))

  protected def screenSizeChanged(): Unit = {
    stage.setViewport(new FitViewport(width.toFloat, height.toFloat))
  }

  override def render(context: RenderContext): Unit = {
    children
    stage.draw()
  }

  override def update(delta: Double): Unit = {
    stage.act(delta.toFloat)
    input.update(delta)
  }

  private[sgine] object screenAdapter extends gdx.ScreenAdapter {
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

    override def resize(width: Int, height: Int): Unit = {
      stage.getViewport.update(width, height, true)
    }

    override def hide(): Unit = {
      Gdx.input.setInputProcessor(null)
    }
  }

  private val inputProcessor = new InputProcessor(this)

  class ScreenPointerEvents extends PointerEvents {
    private val _active: Var[Component] = Var(self)

    def active: Val[Component] = _active

    moved.attach { evt =>
      _active @= evt.target
    }
    active.changes {
      case (oldValue, newValue) =>
        oldValue match {
          case screen: Screen => screen.pointer._over @= false
          case ic: InteractiveComponent => ic.pointer._over @= false
        }
        newValue match {
          case screen: Screen => screen.pointer._over @= true
          case ic: InteractiveComponent => ic.pointer._over @= true
        }
    }
  }
}

object Screen {
  case object Blank extends Screen {
    override protected lazy val root: Component = new Component {
    }
  }
}