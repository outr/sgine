package org.sgine.screen

import com.badlogic.gdx.Gdx
import org.sgine._
import org.sgine.component.Component
import org.sgine.widget.Label

trait FPSLoggingSupport extends Screen {
  val fpsLabel = new FPSLabel

  def fpsUpdateRate: Double = 0.5

  create.once {
    add(fpsLabel)
  }
  render.every(fpsUpdateRate, runNow = true) {
    fpsLabel.text := s"${Gdx.graphics.getFramesPerSecond}fps"
  }

  override protected def add[C <: Component](child: C): C = {
    super.add(child)
    if (child != fpsLabel) {      // Make sure FPSLabel is always last
      remove(fpsLabel)
      add(fpsLabel)
    }
    child
  }
}

class FPSLabel(implicit scrn: Screen) extends Label("") {
  font.family := "Inconsolata"
  font.style := "Bold"
  font.size := 18
  position.right := ui.width - 10.0
  position.top := ui.height - 10.0
}