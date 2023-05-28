package org.sgine

import com.badlogic.gdx.scenes.scene2d.{Group, Stage}
import com.badlogic.gdx.utils.viewport.FitViewport
import org.sgine.component.{Children, Component, PointerSupport, TypedContainer}
import org.sgine.event.InputProcessor
import org.sgine.event.key.KeyInput
import org.sgine.event.pointer.PointerEvents
import org.sgine.update.Updatable
import reactify._

trait Screen extends Updatable with TypedContainer[Component] with PointerSupport { self =>
  lazy val flatChildren: Val[Vector[Component]] = Val(Component.flatChildren(this))

  /**
   * The `Component` at the current cursor position. If nothing else is at the cursor position the `Screen` will be
   * returned.
   */
  val atCursor: Var[PointerSupport] = Var[PointerSupport](self)

  /**
    * If locked, cannot be removed from Screens
    */
  val locked: Var[Boolean] = Var(false)

  /**
    * The sort priority in Screens
    */
  val priority: Var[Double] = Var(0.0)

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

  protected def component: Component

  override lazy val children: Children[Component] = Children(this, Vector(component))

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

    over.attach { evt =>
      _active @= evt.target
    }
    active.changes {
      case (oldValue, newValue) =>
        oldValue match {
          case screen: Screen => screen.pointer.isOver.asInstanceOf[Var[Boolean]] @= false
          case ic: PointerSupport => ic.pointer.isOver.asInstanceOf[Var[Boolean]] @= false
        }
        newValue match {
          case screen: Screen => screen.pointer.isOver.asInstanceOf[Var[Boolean]] @= true
          case ic: PointerSupport => ic.pointer.isOver.asInstanceOf[Var[Boolean]] @= true
        }
    }
  }
}