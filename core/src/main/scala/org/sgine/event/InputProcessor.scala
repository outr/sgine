package org.sgine.event

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.{Gdx, InputProcessor => GDXInputProcessor}
import org.sgine._
import org.sgine.component.{ActorComponent, Component, PointerSupport}
import org.sgine.event.key.{KeyEvent, KeyState}
import org.sgine.event.pointer._
import reactify._

import scala.annotation.tailrec

class InputProcessor(screen: Screen) extends GDXInputProcessor {
  lazy val interactiveActors: Val[Vector[ActorComponent[Actor] with PointerSupport]] =
    Val(screen.flatChildren.collect {
      case c: ActorComponent[_] with PointerSupport if c.visible && c.pointer.enabled =>
        c.asInstanceOf[ActorComponent[Actor] with PointerSupport]
    })

//  private val gestures = new GestureDetector(this)
  private val vector = new Vector2

  private var displayX: Int = 0
  private var displayY: Int = 0
  private var screenX: Double = 0.0
  private var screenY: Double = 0.0
  private var localX: Double = 0.0
  private var localY: Double = 0.0

  private def atCursor: PointerSupport = screen.atCursor.get
//  private def focused: Option[Component] = screen.focused.get

  private def extract(displayX: Int, displayY: Int): (Double, Double, Double, Double) = {
    val percentX = displayX.toDouble / Gdx.graphics.getWidth.toDouble
    val percentY = displayY.toDouble / Gdx.graphics.getHeight.toDouble
    val screenX = percentX * screen.width()
    val screenY = percentY * screen.height()
    (percentX, percentY, screenX, screenY)
  }

  private def pevt(displayX: Int, displayY: Int)
                  (create: (Double, Double, Double, Double, Component, Double, Double) => PointerEvent): Boolean = {
    val (percentX, percentY, screenX, screenY) = extract(displayX, displayY)
    updateCoordinates(displayX, displayY)

    val evt = create(screenX, screenY, percentX, percentY, atCursor, localX, localY)
    evt match {
      case e: PointerDownEvent => e.target match {
        case s: Screen => s.pointer.down @= e
        case ic: PointerSupport =>
          ic.pointer.down @= e
          screen.pointer.down @= e
      }
      case e: PointerDraggedEvent => e.target match {
        case s: Screen => s.pointer.dragged @= e
        case ic: PointerSupport =>
          ic.pointer.dragged @= e
          screen.pointer.dragged @= e
      }
      case e: PointerMovedEvent => e.target match {
        case s: Screen => s.pointer.moved @= e
        case ic: PointerSupport =>
          ic.pointer.moved @= e
          screen.pointer.moved @= e
      }
      case e: PointerUpEvent => e.target match {
        case s: Screen => s.pointer.up @= e
        case ic: PointerSupport =>
          ic.pointer.up @= e
          screen.pointer.up @= e
      }
    }
    Pointer.event @= evt
    evt.target != screen
  }

  private def updateCoordinates(displayX: Int, displayY: Int): Unit = {
    this.displayX = displayX
    this.displayY = displayY
    vector.set(displayX.toFloat, displayY.toFloat)
    screen.stage.screenToStageCoordinates(vector)
    this.screenX = vector.x
    this.screenY = vector.y

    val hits = interactiveActors.filter { c =>
      vector.set(displayX.toFloat, displayY.toFloat)
      c.actor.screenToLocalCoordinates(vector)
      c.actor.hit(vector.x, vector.y, false) != null
    }
    // TODO: support secondary hits?
    val lastHit = hits.lastOption
    lastHit match {
      case Some(widget) =>
        if (atCursor != widget) {
          screen.atCursor @= widget
        }
        vector.set(displayX.toFloat, displayY.toFloat)
        widget.actor.screenToLocalCoordinates(vector)
        this.localX = vector.x
        this.localY = vector.y
      case None =>
        screen.atCursor @= screen
        this.localX = screenX
        this.localY = screenY
    }
  }

  @tailrec
  final def findTouchable(actor: Actor): Actor = {
    if (actor != null && actor.isTouchable) {
      actor
    } else if (actor == null || actor.getParent == null) {
      screen.stage.getRoot
    } else {
      findTouchable(actor.getParent)
    }
  }

  override def keyDown(keyCode: Int): Boolean = {
    val evt = KeyEvent(KeyState.Down, Key(keyCode), screen)
    screen.input.keyDown @= evt
    Keyboard.keyDown @= evt
    true
  }

  override def keyUp(keyCode: Int): Boolean = {
    val evt = KeyEvent(KeyState.Up, Key(keyCode), screen)
    screen.input.keyUp @= evt
    Keyboard.keyUp @= evt
    true
  }

  override def keyTyped(character: Char): Boolean = {
    val evt = TypedEvent(character, screen)
    screen.input.typed @= evt
    Keyboard.typed @= evt
    true
  }

  private var downEvent: Option[PointerDownEvent] = None
  private var lastMove: Option[(Double, Double)] = None

  override def touchDown(displayX: Int, displayY: Int, pointer: Int, button: Int): Boolean = {
    pevt(displayX, displayY) {
      case (screenX, screenY, percentX, percentY, target, targetX, targetY) =>
        val evt = PointerDownEvent(
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
        downEvent = Some(evt)
        evt
    }
  }

  override def touchUp(displayX: Int, displayY: Int, pointer: Int, button: Int): Boolean = {
    downEvent = None
    lastMove = None
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
  }

  override def touchDragged(displayX: Int, displayY: Int, pointer: Int): Boolean = {
    downEvent match {
      case Some(evt) =>
        val (percentX, percentY, screenX, screenY) = extract(displayX, displayY)

        val (deltaX, deltaY) = lastMove match {
          case Some((lastX, lastY)) => (screenX - lastX, screenY - lastY)
          case None => (0.0, 0.0)
        }
        lastMove = Some((screenX, screenY))
        val dragged = PointerDraggedEvent(
          displayX = displayX,
          displayY = displayY,
          screenX = screenX,
          screenY = screenY,
          deltaX = deltaX,
          deltaY = deltaY,
          percentX = percentX,
          percentY = percentY,
          target = evt.target,
          targetX = evt.targetX,
          targetY = evt.targetY,
          pointer = pointer
        )
        evt.target.asInstanceOf[PointerSupport].pointer.dragged @= dragged
        true
      case None => false
    }
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
  }

  override def scrolled(amountX: Float, amountY: Float): Boolean = {
    // TODO: Support
    false
  }

  /*override def pinchStop(): Unit = {
  }

  override def touchDown(x: Float, y: Float, pointer: Int, button: Int): Boolean = {
    true
  }

  override def longPress(x: Float, y: Float): Boolean = {
    atCursor.mouse.longPressed := MouseEvent
    screen.mouse.longPressed := MouseEvent
    ui.mouse.longPressed := MouseEvent
    true
  }

  override def zoom(initialDistance: Float, distance: Float): Boolean = {
    val evt = ZoomEvent(-1, displayX, displayY, screenX, screenY, localX, localY, atCursor, initialDistance, distance, focused)
    atCursor.zoomed := evt
    screen.zoomed := evt
    ui.zoomed := evt
    true
  }

  override def pan(x: Float, y: Float, deltaX: Float, deltaY: Float): Boolean = {
    val evt = PanEvent(-1, displayX, displayY, screenX, screenY, localX, localY, atCursor, deltaX, deltaY, focused)
    atCursor.pan.start := evt
    screen.pan.start := evt
    ui.pan.start := evt
    true
  }

  override def panStop(x: Float, y: Float, pointer: Int, button: Int): Boolean = {
    atCursor.pan.stop := MouseEvent
    screen.pan.stop := MouseEvent
    ui.pan.stop := MouseEvent
    true
  }

  override def tap(x: Float, y: Float, count: Int, button: Int): Boolean = {
    val evt = MouseEvent(button, displayX, displayY, screenX, screenY, localX, localY, atCursor, focused)
    atCursor.mouse.tapped := evt
    screen.mouse.tapped := evt
    ui.mouse.tapped := evt
    true
  }

  override def fling(velocityX: Float, velocityY: Float, button: Int): Boolean = {
    val evt = FlingEvent(button, displayX, displayY, screenX, screenY, localX, localY, atCursor, velocityX, velocityY, focused)
    atCursor.flung := evt
    screen.flung := evt
    ui.flung := evt
    true
  }

  override def pinch(initialPointer1: Vector2, initialPointer2: Vector2, pointer1: Vector2, pointer2: Vector2): Boolean = {
    val evt = PinchEvent(-1, displayX, displayY, screenX, screenY, localX, localY, atCursor, initialPointer1, initialPointer2, pointer1, pointer2, focused)
    atCursor.pinched := evt
    screen.pinched := evt
    ui.pinched := evt
    true
  }*/
}