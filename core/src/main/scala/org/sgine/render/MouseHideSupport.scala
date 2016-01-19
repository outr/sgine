package org.sgine.render

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.{Cursor, Pixmap}
import org.sgine.UI
import pl.metastack.metarx.{ReadStateChannel, Sub}

trait MouseHideSupport extends UI {
  object mouseHide {
    /**
      * The currently elapsed time since any mouse movement has occurred.
      */
    val elapsed: Sub[Double] = Sub(0.0)

    /**
      * The amount of time (in seconds) to delay after mouse movement before hiding the mouse again.
      *
      * Defaults to 5.0
      */
    val delay: Sub[Double] = Sub(5.0)

    /**
      * Determines whether mouse hiding is currently enabled. If disabled the mouse will revert back to the pointer.
      *
      * Defaults to true
      */
    val enabled: Sub[Boolean] = Sub(true)

    /**
      * Resets the elapsed state to keep the mouse from hiding.
      */
    def reset(): Unit = {
      elapsed := 0.0
      _mouseShowing := true
    }

    mouse.moved.attach { evt =>
      reset()
    }

    // Whether the mouse is currently being displayed
    private val _mouseShowing = Sub(true)
    def mouseShowing: ReadStateChannel[Boolean] = _mouseShowing

    create.once {
      _mouseShowing.attach { b =>
        if (b) {
          Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow)
        } else {
          Gdx.graphics.setCursor(MouseHideSupport.hiddenCursor)
        }
      }
    }

    render.on {
      if (_mouseShowing.get) {
        if (enabled.get) {
          elapsed := elapsed.get + delta

          if (elapsed.get >= delay.get) {
            _mouseShowing := false
          }
        }
      } else {
        if (!enabled.get) {   // Revert to the system arrow
          _mouseShowing := true
        }
      }
    }
  }
  mouseHide   // Make sure the object is loaded
}

object MouseHideSupport {
  lazy val hiddenCursor = Gdx.graphics.newCursor(new Pixmap(2, 2, Pixmap.Format.RGBA8888), 1, 1)
}