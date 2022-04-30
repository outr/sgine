package org.sgine.event.key

import org.sgine.Key
import reactify.Channel
import reactify.reaction.Reaction

class KeyChannel extends Channel[KeyEvent]() {
  def onKey(key: Key)(f: => Unit): Reaction[KeyEvent] = {
    attach { evt =>
      if (evt.key == key) {
        f
      }
    }
  }
}