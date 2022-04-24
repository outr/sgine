package org.sgine.component

import com.badlogic.gdx.Gdx
import org.sgine.UI
import reactify._

class FPSView extends Label {
  font := UI.fpsFont

  updates.on {
    text @= s"${Gdx.graphics.getFramesPerSecond}fps"
  }
}