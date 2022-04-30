package org.sgine

import com.badlogic.gdx.scenes.scene2d.{Group, Stage}
import com.badlogic.gdx.utils.viewport.FitViewport
import org.sgine.component.{Children, Component, FPSView, InteractiveComponent, TypedContainer}
import org.sgine.event.key.KeyInput
import org.sgine.event.InputProcessor
import org.sgine.event.pointer.PointerEvents
import org.sgine.update.Updatable
import reactify._

trait Screen extends Updatable with TypedContainer[Component] with InteractiveComponent { self =>
  /**
   * The `Component` at the current cursor position. If nothing else is at the cursor position the `Screen` will be
   * returned.
   */
  val atCursor: Var[InteractiveComponent] = Var[InteractiveComponent](self)

  width @= 3840.0
  height @= 2160.0
  color @= Color.Black

  lazy val stage = new Stage()

  width.and(height).on(screenSizeChanged())

  override lazy val actor: Group = {
    screenSizeChanged()
    stage.getRoot
  }

  override lazy val pointer: ScreenPointerEvents = new ScreenPointerEvents

  lazy val input = new KeyInput

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

  def renderScreen(delta: Double): Unit = {
    children
    render @= delta
    stage.draw()
  }

  override def update(delta: Double): Unit = {
    stage.act(delta.toFloat)
    input.update(delta)
  }

  private[sgine] val inputProcessor = new InputProcessor(this)

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