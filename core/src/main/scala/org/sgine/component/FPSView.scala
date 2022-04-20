package org.sgine.component

import com.badlogic.gdx.Gdx
import org.sgine.UI
import org.sgine.render.RenderContext
import reactify._

class FPSView extends TextView {
  font := UI.fpsFont

  override def render(context: RenderContext): Unit = {
    text @= s"${Gdx.graphics.getFramesPerSecond}fps"

    super.render(context)
  }
}