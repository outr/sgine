package org.sgine

import com.badlogic.gdx
import reactify._

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

  override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
    UI.screens.foreach(_.inputProcessor.touchDown(screenX, screenY, pointer, button))
    true
  }

  override def touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
    UI.screens.foreach(_.inputProcessor.touchUp(screenX, screenY, pointer, button))
    true
  }

  override def touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = {
    UI.screens.foreach(_.inputProcessor.touchDragged(screenX, screenY, pointer))
    true
  }

  override def mouseMoved(screenX: Int, screenY: Int): Boolean = {
    UI.screens.foreach(_.inputProcessor.mouseMoved(screenX, screenY))
    true
  }

  override def scrolled(amountX: Float, amountY: Float): Boolean = {
    UI.screens.foreach(_.inputProcessor.scrolled(amountX, amountY))
    true
  }
}