package org.sgine.component

import com.badlogic.gdx.Gdx
import org.sgine.render.RenderContext
import org.sgine.Overlay
import reactify._

object FPSView extends Label {
  font := RenderContext.fontNormal
  top @= 0.0
  right @= Overlay.width - 10.0

  updates.on {
    text @= s"${Gdx.graphics.getFramesPerSecond}fps"
  }
}