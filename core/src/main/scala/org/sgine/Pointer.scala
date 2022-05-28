package org.sgine

import org.sgine.event.pointer.{PointerEvent, PointerMovedEvent}
import reactify.{Channel, Val, Var}

object Pointer {
  val event: Channel[PointerEvent] = Channel[PointerEvent]

  private val displayX: Var[Int] = Var(0)
  private val displayY: Var[Int] = Var(0)

  private val screenX: Var[Double] = Var(0.0)
  private val screenY: Var[Double] = Var(0.0)

  object display {
    def x: Val[Int] = displayX
    def y: Val[Int] = displayY
  }

  object screen {
    def x: Val[Double] = screenX
    def y: Val[Double] = screenY
  }

  event.attach {
    case evt: PointerMovedEvent =>
      displayX @= evt.displayX
      displayY @= evt.displayY
      screenX @= evt.screenX
      screenY @= evt.screenY
    case _ => // Ignore
  }
}
