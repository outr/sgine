package org.sgine.event

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.{Cursor, Pixmap}
import org.sgine.{Pointer, UI}
import reactify._

trait MouseHideSupport {
  object mouseHide {
    /**
      * The currently elapsed time since any mouse movement has occurred.
      */
    val elapsed: Var[Double] = Var(0.0)

    /**
      * The amount of time (in seconds) to delay after mouse movement before hiding the mouse again.
      *
      * Defaults to 5.0
      */
    val delay: Var[Double] = Var(5.0)

    /**
      * Determines whether mouse hiding is currently enabled. If disabled the mouse will revert back to the pointer.
      *
      * Defaults to true
      */
    val enabled: Var[Boolean] = Var(true)

    /**
      * Resets the elapsed state to keep the mouse from hiding.
      */
    def reset(): Unit = {
      elapsed := 0.0
      _mouseShowing := true
    }

    (Pointer.display.x & Pointer.display.y).on {
      reset()
    }

    // Whether the mouse is currently being displayed
    private val _mouseShowing = Var(true)
    def mouseShowing: Val[Boolean] = _mouseShowing

    _mouseShowing.attach { b =>
      if (b) {
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow)
      } else {
        Gdx.graphics.setCursor(MouseHideSupport.hiddenCursor)
      }
    }

    UI.render.attach { delta =>
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
}

object MouseHideSupport {
  lazy val hiddenCursor: Cursor = Gdx.graphics.newCursor(new Pixmap(2, 2, Pixmap.Format.RGBA8888), 1, 1)
}