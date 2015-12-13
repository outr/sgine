package org.sgine.event

import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.input.GestureDetector.GestureListener
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.{Gdx, InputProcessor => GDXInputProcessor}
import org.sgine.Screen
import org.sgine.component.Component
import org.sgine.input.Key
import pl.metastack.metarx.Channel

class InputProcessor(screen: Screen) extends GDXInputProcessor with GestureListener{
  private val gestures = new GestureDetector(this)

  // TODO: add focused and atCursor support
  val focused: Option[Component] = None
  val atCursor: Option[Component] = None

  def fireKeyEvent(keyCode: Int, channel: Channel[KeyEvent], functions: (Int => Boolean)*): Boolean = {
    Key.byCode(keyCode) match {
      case Some(key) => channel := KeyEvent(key, focused, atCursor)
      case None => Gdx.app.log("Unsupported Key Code", s"Unsupported keyCode: $keyCode in InputProcessor.fireKeyEvent.")
    }
    functions.foreach(_.apply(keyCode))
    true
  }

  override def keyDown(keycode: Int): Boolean = fireKeyEvent(keycode, screen.key.down, gestures.keyDown)

  override def keyUp(keycode: Int): Boolean = fireKeyEvent(keycode, screen.key.up, gestures.keyUp)

  override def keyTyped(character: Char): Boolean = {
    Key.byChar(character) match {
      case Some(key) => screen.key.typed := KeyEvent(key, focused, atCursor)
      case None => Gdx.app.log("Unsupported Key Code", s"Unsupported keyChar: $character in InputProcessor.keyTyped.")
    }
    gestures.keyTyped(character)
    true
  }

  override def mouseMoved(screenX: Int, screenY: Int): Boolean = ???

  override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = ???

  override def touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = ???

  override def touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = ???

  override def scrolled(amount: Int): Boolean = ???

  override def touchDown(x: Float, y: Float, pointer: Int, button: Int): Boolean = ???

  override def longPress(x: Float, y: Float): Boolean = ???

  override def zoom(initialDistance: Float, distance: Float): Boolean = ???

  override def pan(x: Float, y: Float, deltaX: Float, deltaY: Float): Boolean = ???

  override def tap(x: Float, y: Float, count: Int, button: Int): Boolean = ???

  override def fling(velocityX: Float, velocityY: Float, button: Int): Boolean = ???

  override def panStop(x: Float, y: Float, pointer: Int, button: Int): Boolean = ???

  override def pinch(initialPointer1: Vector2, initialPointer2: Vector2, pointer1: Vector2, pointer2: Vector2): Boolean = ???
}
