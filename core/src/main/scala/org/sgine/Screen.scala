package org.sgine

import com.badlogic.gdx.scenes.scene2d.{Group, Stage}
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.{Gdx, Screen => GDXScreen}
import org.sgine.component._
import org.sgine.event.InputProcessor
import org.sgine.input.Key
import pl.metastack.metarx.Sub

/**
  * Screen represents a visual section of a UI. UIs can have one or many Screens that are displayed at any given time.
  */
class Screen extends RenderFlow with AbstractContainer with ActorWidget[Group] with InputSupport {
  lazy val stage = new Stage(new ScreenViewport)
  private[sgine] lazy val gdx = new GDXScreenInstance(this)

  lazy val actor: Group = stage.getRoot
  private val inputProcessor = new InputProcessor(this)

  /**
    * The `Component` at the current cursor position. If nothing else is at the cursor position the `Screen` will be
    * returned.
    */
  val atCursor: Sub[Component] = Sub[Component](screen)
  /**
    * The `Component` currently focused. If their is nothing currently focused `None` will be returned.
    */
  val focused: Sub[Option[Focusable]] = Sub[Option[Focusable]](None)

  implicit def thisScreen: Screen = this

  create.once {
    var oldValue: Option[Focusable] = None
    focused.attach { newValue =>
      oldValue.foreach(_.blur.exec())
      newValue.foreach(_.focus.exec())
      oldValue = newValue
    }
  }

  resize.on {
    stage.getViewport.update(Gdx.graphics.getWidth, Gdx.graphics.getHeight, true)
    stage.getRoot.setWidth(Gdx.graphics.getWidth)
    stage.getRoot.setHeight(Gdx.graphics.getHeight)
  }

  show.on {
    Gdx.input.setInputProcessor(inputProcessor)
  }
  hide.on {
    if (Gdx.input.getInputProcessor == inputProcessor) {
      Gdx.input.setInputProcessor(null)
    }
  }

  render.on {
    stage.act(Gdx.graphics.getDeltaTime)
    stage.draw()
  }

  dispose.on {
    stage.dispose()
  }

  override def screen: Screen = this

  /**
    * Add a Component to this Screen
    */
  def +=(child: Component) = add(child)

  /**
    * Remove a Component from this Screen
    */
  def -=(child: Component) = remove(child)

  override protected def add[C <: Component](child: C): C = {
    super.add(child)
    render.once {
      child match {
        case ac: ActorComponent[_] => screen.stage.getRoot.addActor(ac.actor)
        case _ => // Ignore non-actor components
      }
    }
    child
  }

  override protected def remove[C <: Component](child: C): C = {
    super.remove(child)
    render.once {
      child match {
        case ac: ActorComponent[_] => screen.stage.getRoot.removeActor(ac.actor)
        case _ => // Ignore non-actor components
      }
    }
    child
  }

  /**
    * Simulates the down, typed, and release states on this screen for the provided `key`.
    */
  def simulate(key: Key): Unit = {
    inputProcessor.keyDown(key.code)
    inputProcessor.keyTyped(key.lowerCase.getOrElse(0))
    inputProcessor.keyUp(key.code)
  }
}

class GDXScreenInstance(screen: Screen) extends GDXScreen {
  override def show(): Unit = {
    if (screen.create.nonEmpty) {
      screen.create.exec()
      screen.create.clear()
    }
    screen.show.exec()
  }

  override def hide(): Unit = screen.hide.exec()

  override def render(delta: Float): Unit = screen.render.exec()

  override def resize(width: Int, height: Int): Unit = screen.resize.exec()

  override def pause(): Unit = screen.pause.exec()

  override def resume(): Unit = screen.resume.exec()

  override def dispose(): Unit = screen.dispose.exec()
}