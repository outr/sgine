package org.sgine.render

import com.badlogic.gdx.Gdx
import org.sgine.UI

trait SingleScreenSupport extends UI {
  create.once {
    screen.gdx.show()
    screen.gdx.resize(Gdx.graphics.getWidth, Gdx.graphics.getHeight)
  }
  render.on {
    screen.gdx.render(Gdx.graphics.getDeltaTime)
  }
  resize.on {
    screen.gdx.resize(Gdx.graphics.getWidth, Gdx.graphics.getHeight)
  }
  dispose.on {
    screen.dispose.exec()
  }
}
