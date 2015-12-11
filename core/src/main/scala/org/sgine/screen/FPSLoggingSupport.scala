package org.sgine.screen

import com.badlogic.gdx.graphics.FPSLogger
import org.sgine.Screen

trait FPSLoggingSupport extends Screen {
  private val fps = new FPSLogger

  render.on {
    fps.log()
  }
}
