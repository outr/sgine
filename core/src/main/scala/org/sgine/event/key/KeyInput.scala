package org.sgine.event.key

import org.sgine.Key
import org.sgine.event.TypedEvent
import org.sgine.update.Updatable
import reactify._

class KeyInput extends Updatable {
  val Repeat: Var[Double] = Var(0.01)

  private val _pressed = Var(Map.empty[Key, KeyEvent])

  val pressed: Val[Set[Key]] = Val(_pressed.keySet)

  val keyDown: KeyChannel = new KeyChannel
  val keyUp: KeyChannel = new KeyChannel
  val keyTyped: KeyChannel = new KeyChannel
  val typed: Channel[TypedEvent] = Channel[TypedEvent]

  keyDown.attach { evt =>
    _pressed @= _pressed() + (evt.key -> evt)
  }
  keyUp.attach { evt =>
    _pressed @= _pressed() - evt.key
    keyTyped @= evt
  }

  override def update(delta: Double): Unit = {
    val now = System.currentTimeMillis()
    _pressed.foreach {
      case (_, evt) =>
        val elapsed = now - evt.time
        if (elapsed >= Repeat * 1000.0) {
          val typedEvent = evt.copy(time = now)
          _pressed @= _pressed + (evt.key -> typedEvent)
          keyTyped @= typedEvent
        }
    }
  }
}
