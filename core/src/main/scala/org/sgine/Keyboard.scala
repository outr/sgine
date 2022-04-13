package org.sgine

import org.sgine.event.key.KeyEvent
import reactify.Channel

object Keyboard {
  // TODO: Fire events here from Screen
  val event: Channel[KeyEvent] = Channel[KeyEvent]
}