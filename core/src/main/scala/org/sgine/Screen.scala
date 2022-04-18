package org.sgine

import com.badlogic.gdx
import com.badlogic.gdx.{Gdx, InputProcessor}
import com.badlogic.gdx.graphics.{Camera, Color, GL20, OrthographicCamera}
import com.badlogic.gdx.math.Vector3
import org.sgine.component.{Children, Component, Container, FPSView, InteractiveComponent, TextView, TypedContainer}
import org.sgine.event.pointer.{PointerButton, PointerDownEvent, PointerDraggedEvent, PointerEvent, PointerEvents, PointerMovedEvent, PointerUpEvent}
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
  lazy val interactive: Val[List[InteractiveComponent]] = Val(flatChildren.collect {
    case ic: InteractiveComponent => ic
  }.filter(c => c.isVisible && c.interactive).sortBy(_.depth()))

  val width: Var[Double] = Var(3840.0)
  val height: Var[Double] = Var(2160.0)

  val center: Val[Double] = Val(width / 2.0)
  val middle: Val[Double] = Val(height / 2.0)

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
      // TODO: fix target ordering
//      scribe.info(s"Interactive: ${interactive()}")
      val evt = interactive.collectFirst {
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

    override def scrolled(amountX: Float, amountY: Float): Boolean = true
  }
}

object Screen {
  case object Blank extends Screen {
    override protected lazy val root: Component = new Component {
    }
  }
}