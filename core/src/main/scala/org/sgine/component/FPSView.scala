package org.sgine.component

import com.badlogic.gdx.Gdx
import org.sgine.{Overlay, UI}
import reactify._

object FPSView extends Label {
  font := UI.fontNormal
  top @= 0.0
  right @= Overlay.width - 10.0

  updates.on {
    text @= s"${Gdx.graphics.getFramesPerSecond}fps"
  }
}