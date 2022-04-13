package org.sgine

import org.sgine.event.pointer.{PointerEvent, PointerMovedEvent}
import reactify.{Channel, Val, Var}

object Pointer {
  // TODO: Fire events here from Screen
  val event: Channel[PointerEvent] = Channel[PointerEvent]

  private val _x: Var[Int] = Var[Int](0)
  private val _y: Var[Int] = Var[Int](0)

  def x: Val[Int] = _x
  def y: Val[Int] = _y

  event.attach {
    case evt: PointerMovedEvent =>
      _x @= evt.displayX
      _y @= evt.displayY
    case _ => // Ignore
  }
}
