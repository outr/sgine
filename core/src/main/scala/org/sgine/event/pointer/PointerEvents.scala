package org.sgine.event.pointer

import reactify._

class PointerEvents {
  private[sgine] lazy val _over: Var[Boolean] = Var(false)

  lazy val down: Channel[PointerDownEvent] = Channel[PointerDownEvent]
  lazy val dragged: Channel[PointerDraggedEvent] = Channel[PointerDraggedEvent]
  lazy val moved: Channel[PointerMovedEvent] = Channel[PointerMovedEvent]
  lazy val up: Channel[PointerUpEvent] = Channel[PointerUpEvent]
  def over: Val[Boolean] = _over
}