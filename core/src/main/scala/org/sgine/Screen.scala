package org.sgine

import com.badlogic.gdx
import com.badlogic.gdx.{Gdx, InputProcessor}
import com.badlogic.gdx.graphics.{Camera, GL20, OrthographicCamera}
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.{Group, Stage}
import com.badlogic.gdx.utils.viewport.ScreenViewport
import org.sgine.component.{Children, Component, Container, FPSView, GroupContainer, InteractiveComponent, TextView, TypedContainer}
import org.sgine.event.key.{KeyEvent, KeyState}
import org.sgine.event.TypedEvent
import org.sgine.event.pointer.{PointerButton, PointerDownEvent, PointerDraggedEvent, PointerEvent, PointerEvents, PointerMovedEvent, PointerUpEvent}
import org.sgine.render.{RenderContext, Renderable}
import org.sgine.update.Updatable
import reactify._

trait Screen extends Renderable with Updatable with GroupContainer { self =>
  lazy val flatChildren: Val[List[Component]] = Val(TypedContainer.flatChildren(children: _*))
  lazy val renderables: Val[List[Renderable]] = Val(flatChildren.collect {
    case r: Renderable => r
  }.filter(_.shouldRender).sorted(Renderable.ordering))
  lazy val updatables: Val[List[Updatable]] = Val(flatChildren.collect {
    case u: Updatable => u
  })
  lazy val interactive: Val[List[InteractiveComponent]] = Val(flatChildren.collect {
    case ic: InteractiveComponent => ic
  }.filter(c => c.isVisible && c.interactive).sortBy(_.depth()))

  lazy val stage = new Stage(new ScreenViewport)
  override lazy val actor: Group = stage.getRoot

  width @= 3840.0
  height @= 2160.0

  object pointer extends PointerEvents {
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

  object fpsView extends FPSView {
    visible := UI.drawFPS
    top @= 0.0
    right := self.width - 10.0
  }

  override lazy val children: Children[Component] = Children(this, List(root, fpsView))

  override def render(context: RenderContext): Unit = {
    stage.getViewport.update(Gdx.graphics.getWidth, Gdx.graphics.getHeight, true)
    stage.getRoot.setWidth(Gdx.graphics.getWidth)
    stage.getRoot.setHeight(Gdx.graphics.getHeight)
//    renderables().foreach(_.render(context))
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

//      _context.renderWith {
//        self.render(_context)
//      }

      stage.getViewport.update(Gdx.graphics.getWidth, Gdx.graphics.getHeight, true)
      stage.getRoot.setWidth(Gdx.graphics.getWidth)
      stage.getRoot.setHeight(Gdx.graphics.getHeight)
      stage.draw()
    }

    override def hide(): Unit = {
      Gdx.input.setInputProcessor(null)
    }
  }

  private object inputProcessor extends InputProcessor {
    override def keyDown(keyCode: Int): Boolean = {
      val evt = KeyEvent(KeyState.Down, Key(keyCode), self)
      input.keyDown @= evt
      Keyboard.keyDown @= evt
      true
    }

    override def keyUp(keyCode: Int): Boolean = {
      val evt = KeyEvent(KeyState.Up, Key(keyCode), self)
      input.keyUp @= evt
      Keyboard.keyUp @= evt
      true
    }

    override def keyTyped(character: Char): Boolean = {
      val evt = TypedEvent(character, self)
      input.typed @= evt
      Keyboard.typed @= evt
      true
    }

    private lazy val v3 = new Vector3

    private def pevt(displayX: Int, displayY: Int)
                    (create: (Double, Double, Double, Double, Component, Double, Double) => PointerEvent): Unit = {
      val percentX = displayX.toDouble / Gdx.graphics.getWidth.toDouble
      val percentY = displayY.toDouble / Gdx.graphics.getHeight.toDouble
      val screenX = percentX * width()
      val screenY = percentY * height()
      def doHitTest(ic: InteractiveComponent): Boolean = {
        v3.set(screenX.toFloat, screenY.toFloat, 0.0f)
        ic.hitTest(v3)
      }
      val evt = interactive.reverse.collectFirst {
        case ic if doHitTest(ic) => create(screenX, screenY, percentX, percentY, ic, v3.x, v3.y)
      }.getOrElse(create(screenX, screenY, percentX, percentY, self, screenX, screenY))
      evt match {
        case e: PointerDownEvent =>
          e.target match {
            case s: Screen => s.pointer.down @= e
            case ic: InteractiveComponent =>
              ic.pointer.down @= e
              self.pointer.down @= e
          }
        case e: PointerDraggedEvent =>
          e.target match {
            case s: Screen => s.pointer.dragged @= e
            case ic: InteractiveComponent =>
              ic.pointer.dragged @= e
              self.pointer.dragged @= e
          }
        case e: PointerMovedEvent =>
          e.target match {
            case s: Screen => s.pointer.moved @= e
            case ic: InteractiveComponent =>
              ic.pointer.moved @= e
              self.pointer.moved @= e
          }
        case e: PointerUpEvent =>
          e.target match {
            case s: Screen => s.pointer.up @= e
            case ic: InteractiveComponent =>
              ic.pointer.up @= e
              self.pointer.up @= e
          }
      }
      Pointer.event @= evt
    }

    override def touchDown(displayX: Int, displayY: Int, pointer: Int, button: Int): Boolean = {
      pevt(displayX, displayY) {
        case (screenX, screenY, percentX, percentY, target, targetX, targetY) => PointerDownEvent(
          displayX = displayX,
          displayY = displayY,
          screenX = screenX,
          screenY = screenY,
          percentX = percentX,
          percentY = percentY,
          target = target,
          targetX = targetX,
          targetY = targetY,
          pointer = pointer,
          button = PointerButton(button)
        )
      }
      true
    }

    override def touchUp(displayX: Int, displayY: Int, pointer: Int, button: Int): Boolean = {
      pevt(displayX, displayY) {
        case (screenX, screenY, percentX, percentY, target, targetX, targetY) => PointerUpEvent(
          displayX = displayX,
          displayY = displayY,
          screenX = screenX,
          screenY = screenY,
          percentX = percentX,
          percentY = percentY,
          target = target,
          targetX = targetX,
          targetY = targetY,
          pointer = pointer,
          button = PointerButton(button)
        )
      }
      true
    }

    override def touchDragged(displayX: Int, displayY: Int, pointer: Int): Boolean = {
      pevt(displayX, displayY) {
        case (screenX, screenY, percentX, percentY, target, targetX, targetY) => PointerDraggedEvent(
          displayX = displayX,
          displayY = displayY,
          screenX = screenX,
          screenY = screenY,
          percentX = percentX,
          percentY = percentY,
          target = target,
          targetX = targetX,
          targetY = targetY,
          pointer = pointer
        )
      }
      true
    }

    override def mouseMoved(displayX: Int, displayY: Int): Boolean = {
      pevt(displayX, displayY) {
        case (screenX, screenY, percentX, percentY, target, targetX, targetY) => PointerMovedEvent(
          displayX = displayX,
          displayY = displayY,
          screenX = screenX,
          screenY = screenY,
          percentX = percentX,
          percentY = percentY,
          target = target,
          targetX = targetX,
          targetY = targetY
        )
      }
      true
    }

    override def scrolled(amountX: Float, amountY: Float): Boolean = {
      // TODO: Support
      true
    }
  }
}

object Screen {
  case object Blank extends Screen {
    override protected lazy val root: Component = new Component {
    }
  }
}