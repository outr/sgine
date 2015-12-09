package org.sgine.render

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import org.sgine.UI

trait ClearScreenSupport extends UI {
  render.on {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
  }
}
