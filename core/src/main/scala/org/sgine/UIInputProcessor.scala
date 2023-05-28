package org.sgine

import com.badlogic.gdx
import reactify._

import scala.annotation.tailrec

class UIInputProcessor extends gdx.InputProcessor {
  override def keyDown(keycode: Int): Boolean = {
    UI.screens.foreach(_.inputProcessor.keyDown(keycode))
    true
  }

  override def keyUp(keycode: Int): Boolean = {
    UI.screens.foreach(_.inputProcessor.keyUp(keycode))
    true
  }

  override def keyTyped(character: Char): Boolean = {
    UI.screens.foreach(_.inputProcessor.keyTyped(character))
    true
  }

  @tailrec
  private def recurseScreens(f: => Screen => Boolean,
                             screens: Vector[Screen] = UI.screens,
                             position: Int = 0): Boolean = if (position == screens.length) {
    false
  } else {
    val screen = screens(screens.length - position - 1)
    if (f(screen)) {
      true
    } else {
      recurseScreens(f, screens, position + 1)
    }
  }

  override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
    recurseScreens(_.inputProcessor.touchDown(screenX, screenY, pointer, button))
  }

  override def touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
    recurseScreens(_.inputProcessor.touchUp(screenX, screenY, pointer, button))
  }

  override def touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = {
    recurseScreens(_.inputProcessor.touchDragged(screenX, screenY, pointer))
  }

  override def mouseMoved(screenX: Int, screenY: Int): Boolean = {
    recurseScreens(_.inputProcessor.mouseMoved(screenX, screenY))
  }

  def checkOver(): Unit = recurseScreens(s => {
    s.inputProcessor.checkOver()
    false
  })

  override def scrolled(amountX: Float, amountY: Float): Boolean = {
    recurseScreens(_.inputProcessor.scrolled(amountX, amountY))
    true
  }
}