package org.sgine.event

import java.util.concurrent.atomic.AtomicBoolean

import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.input.GestureDetector.GestureListener
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.{Gdx, InputProcessor => GDXInputProcessor}
import org.sgine._
import org.sgine.component.{ActorWidget, Component}
import org.sgine.input.Key
import reactify._

import scala.annotation.tailrec

class InputProcessor(screen: Screen) extends GDXInputProcessor with GestureListener{
  private val gestures = new GestureDetector(this)
  private val vector = new Vector2

  private var screenX: Int = 0
  private var screenY: Int = 0
  private var stageX: Double = 0.0
  private var stageY: Double = 0.0
  private var localX: Double = 0.0
  private var localY: Double = 0.0

  private def atCursor: Component = screen.atCursor.get
  private def focused: Option[Component] = screen.focused.get

  def fireKeyEvent(keyCode: Int,
                   gestureFunction: Int => Boolean,
                   stageFunction: Int => Boolean,
                   componentChannel: Channel[KeyEvent],
                   screenChannel: Channel[KeyEvent],
                   uiChannel: Channel[KeyEvent]): Boolean = {
    Key.byCode(keyCode) match {
      case Some(key) => {
        val evt = KeyEvent(key, atCursor, focused)
        if (componentChannel != screenChannel) {
          componentChannel := evt
        }
        screenChannel := evt
        uiChannel := evt
      }
      case None => Gdx.app.log("Unsupported Key Code", s"Unsupported keyCode: $keyCode in InputProcessor.fireKeyEvent.")
    }
    gestureFunction(keyCode)
    stageFunction(keyCode)
    true
  }

  def fireMouseEvent(componentChannel: Channel[MouseEvent],
                     screenChannel: Channel[MouseEvent],
                     uiChannel: Channel[MouseEvent],
                     button: Int = -1,
                     screenX: Int = screenX,
                     screenY: Int = screenY,
                     stageX: Double = stageX,
                     stageY: Double = stageY,
                     localX: Double = localX,
                     localY: Double = localY,
                     component: Component = atCursor,
                     focused: Option[Component] = this.focused): Boolean = {
    val evt = MouseEvent(button, screenX, screenY, stageX, stageY, localX, localY, component, focused)
    if (componentChannel != screenChannel) {
      componentChannel := evt
    }
    screenChannel := evt
    uiChannel := evt
    true
  }

  private def updateCoordinates(screenX: Int, screenY: Int): Unit = {
    this.screenX = screenX
    this.screenY = screenY
    vector.set(screenX, screenY)
    screen.stage.screenToStageCoordinates(vector)
    this.stageX = vector.x
    this.stageY = vector.y

    val touchable: Boolean = true
    val actor = screen.stage.hit(stageX.toFloat, stageY.toFloat, touchable)
    val widget = findTouchable(actor).getUserObject.asInstanceOf[ActorWidget[Actor]]
    if (atCursor != widget) {
      screen.atCursor := widget
    }
    vector.set(screenX, screenY)
    widget.actor.screenToLocalCoordinates(vector)
    this.localX = vector.x
    this.localY = vector.y
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

  override def keyDown(keycode: Int): Boolean = fireKeyEvent(keycode, gestures.keyDown, screen.stage.keyDown, focused.getOrElse(screen).key.down, screen.key.down, ui.key.down)

  override def keyUp(keycode: Int): Boolean = fireKeyEvent(keycode, gestures.keyUp, screen.stage.keyUp, focused.getOrElse(screen).key.up, screen.key.up, ui.key.up)

  override def keyTyped(character: Char): Boolean = {
    Key.byChar(character) match {
      case Some(key) => {
        val evt = KeyEvent(key, atCursor, focused)
        focused.getOrElse(screen).key.typed := evt
        screen.key.typed := evt
        ui.key.typed := evt
      }
      case None if character.toInt == 0 => // Ignore non-character keys
      case None => Gdx.app.log("Unsupported Key Code", s"Unsupported keyChar: $character (${character.toInt}) in InputProcessor.keyTyped.")
    }
    gestures.keyTyped(character)
    screen.stage.keyTyped(character)
    true
  }

  private val updateMouseMoved = new AtomicBoolean(false)
  private var lastUpdated: Long = _
  override def mouseMoved(screenX: Int, screenY: Int): Boolean = {
    this.screenX = screenY
    this.screenY = screenY
    val throttling = UI().throttleMouseMove.get
    if (throttling > 0.0) {
      updateMouseMoved.set(true)
      true
    } else {
      processMoved()
    }
  }

  def processMoved(): Boolean = {
    updateMouseMoved.set(false)
    updateCoordinates(screenX, screenY)
    gestures.mouseMoved(screenX, screenY)
    screen.stage.mouseMoved(screenX, screenY)
    fireMouseEvent(atCursor.mouse.moved, screen.mouse.moved, ui.mouse.moved)
  }

  screen.render.on {
    val time = System.currentTimeMillis()
    if (updateMouseMoved.get && lastUpdated < time - math.round(UI().throttleMouseMove.get * 1000.0)) {
      updateMouseMoved.set(false)
      processMoved()
      lastUpdated = time
    }
  }

  override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
    updateCoordinates(screenX, screenY)
    gestures.touchDown(screenX, screenY, pointer, button)
    screen.stage.touchDown(screenX, screenY, pointer, button)
    fireMouseEvent(atCursor.mouse.down, screen.mouse.down, ui.mouse.down, button)
  }

  override def touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = {
    updateCoordinates(screenX, screenY)
    gestures.touchDragged(screenX, screenY, pointer)
    screen.stage.touchDragged(screenX, screenY, pointer)
    fireMouseEvent(atCursor.mouse.dragged, screen.mouse.dragged, ui.mouse.dragged)
  }

  override def touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
    updateCoordinates(screenX, screenY)
    gestures.touchUp(screenX, screenY, pointer, button)
    screen.stage.touchUp(screenX, screenY, pointer, button)
    fireMouseEvent(atCursor.mouse.up, screen.mouse.up, ui.mouse.up, button)
  }

  override def scrolled(amountX: Float, amountY: Float): Boolean = {
    gestures.scrolled(amountX, amountY)
    val evt = ScrollEvent(-1, screenX, screenY, stageX, stageY, localX, localY, atCursor, amountY.toInt, focused)
    atCursor.scrolled := evt
    screen.scrolled := evt
    ui.scrolled := evt
    true
  }

  override def pinchStop(): Unit = {
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
    val evt = ZoomEvent(-1, screenX, screenY, stageX, stageY, localX, localY, atCursor, initialDistance, distance, focused)
    atCursor.zoomed := evt
    screen.zoomed := evt
    ui.zoomed := evt
    true
  }

  override def pan(x: Float, y: Float, deltaX: Float, deltaY: Float): Boolean = {
    val evt = PanEvent(-1, screenX, screenY, stageX, stageY, localX, localY, atCursor, deltaX, deltaY, focused)
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
    val evt = MouseEvent(button, screenX, screenY, stageX, stageY, localX, localY, atCursor, focused)
    atCursor.mouse.tapped := evt
    screen.mouse.tapped := evt
    ui.mouse.tapped := evt
    true
  }

  override def fling(velocityX: Float, velocityY: Float, button: Int): Boolean = {
    val evt = FlingEvent(button, screenX, screenY, stageX, stageY, localX, localY, atCursor, velocityX, velocityY, focused)
    atCursor.flung := evt
    screen.flung := evt
    ui.flung := evt
    true
  }

  override def pinch(initialPointer1: Vector2, initialPointer2: Vector2, pointer1: Vector2, pointer2: Vector2): Boolean = {
    val evt = PinchEvent(-1, screenX, screenY, stageX, stageY, localX, localY, atCursor, initialPointer1, initialPointer2, pointer1, pointer2, focused)
    atCursor.pinched := evt
    screen.pinched := evt
    ui.pinched := evt
    true
  }
}