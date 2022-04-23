package org.sgine

import com.badlogic.gdx
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.{Camera, GL20, OrthographicCamera}
import com.badlogic.gdx.scenes.scene2d.{Group, Stage}
import com.badlogic.gdx.utils.viewport.ScreenViewport
import org.sgine.component.{Children, Component, GroupContainer, InteractiveComponent, TypedContainer}
import org.sgine.event.key.{KeyEvent, KeyState}
import org.sgine.event.{InputProcessor, TypedEvent}
import org.sgine.event.pointer.{PointerButton, PointerDownEvent, PointerDraggedEvent, PointerEvent, PointerEvents, PointerMovedEvent, PointerUpEvent}
import org.sgine.render.{RenderContext, Renderable}
import org.sgine.update.Updatable
import reactify._

trait Screen extends Renderable with Updatable with GroupContainer with InteractiveComponent { self =>
  /**
   * The `Component` at the current cursor position. If nothing else is at the cursor position the `Screen` will be
   * returned.
   */
  val atCursor: Var[InteractiveComponent] = Var[InteractiveComponent](self)

//  lazy val flatChildren: Val[List[Component]] = Val(TypedContainer.flatChildren(children: _*))
//  lazy val renderables: Val[List[Renderable]] = Val(flatChildren.collect {
//    case r: Renderable => r
//  }.filter(_.shouldRender).sorted(Renderable.ordering))
//  lazy val updatables: Val[List[Updatable]] = Val(flatChildren.collect {
//    case u: Updatable => u
//  })
//  lazy val interactive: Val[List[InteractiveComponent]] = Val(flatChildren.collect {
//    case ic: InteractiveComponent => ic
//  }.filter(c => c.isVisible && c.interactive).sortBy(_.depth()))

  lazy val stage = new Stage(new ScreenViewport)
  override lazy val actor: Group = stage.getRoot

  width @= 3840.0
  height @= 2160.0

  override lazy val pointer: ScreenPointerEvents = new ScreenPointerEvents

  object input {
    val keyDown: Channel[KeyEvent] = Channel[KeyEvent]
    val keyUp: Channel[KeyEvent] = Channel[KeyEvent]
    val typed: Channel[TypedEvent] = Channel[TypedEvent]
  }

  protected[sgine] lazy val camera: Camera = {
    val c = new OrthographicCamera
    c.setToOrtho(false, width().toFloat, height().toFloat)
    c.update()
    c
  }

  private lazy val _context = new RenderContext(this)

  protected def root: Component

  /*object fpsView extends FPSView {
    visible := UI.drawFPS
    top @= 0.0
    right := self.width - 10.0
  }*/

  override lazy val children: Children[Component] = Children(this, List(root)) //, fpsView))

  override def render(context: RenderContext): Unit = {
    stage.getViewport.setCamera(camera)
//    renderables().foreach(_.render(context))

    // TODO: Fix resizing bugs
    //Size: 1728x966 / 1728.0x966.0
//    scribe.info(s"Size: ${stage.getViewport.getScreenWidth}x${stage.getViewport.getScreenHeight} / ${stage.getViewport.getWorldWidth}x${stage.getViewport.getWorldHeight}")
//    self.width @= width.toDouble
//    self.height @= height.toDouble
//    stage.getViewport.update(width.toInt, height.toInt, true)
//    stage.getRoot.setWidth(width.toFloat)
//    stage.getRoot.setHeight(height.toFloat)
//    stage.getRoot.setWidth(Gdx.graphics.getWidth)
//    stage.getRoot.setHeight(Gdx.graphics.getHeight)
    children
    stage.draw()
  }

  override def update(delta: Double): Unit = {
//    updatables().foreach(_.update(delta))
    stage.act(delta.toFloat)
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