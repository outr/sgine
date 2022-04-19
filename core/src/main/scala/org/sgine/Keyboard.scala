package org.sgine

import org.sgine.event.TypedEvent
import org.sgine.event.key.KeyEvent
import reactify.Channel

object Keyboard {
  val keyDown: Channel[KeyEvent] = Channel[KeyEvent]
  val keyUp: Channel[KeyEvent] = Channel[KeyEvent]
  val typed: Channel[TypedEvent] = Channel[TypedEvent]
}