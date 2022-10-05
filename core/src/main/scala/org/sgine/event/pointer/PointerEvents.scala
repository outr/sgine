package org.sgine.event.pointer

import org.sgine.{Pointer, PointerCursor}
import reactify._

class PointerEvents {
  lazy val enabled: Var[Boolean] = Var(true)

  private[sgine] lazy val _over: Var[Boolean] = Var(false)

  lazy val down: Channel[PointerDownEvent] = Channel[PointerDownEvent]
  lazy val dragged: Channel[PointerDraggedEvent] = Channel[PointerDraggedEvent]
  lazy val moved: Channel[PointerMovedEvent] = Channel[PointerMovedEvent]
  lazy val up: Channel[PointerUpEvent] = Channel[PointerUpEvent]
  def over: Val[Boolean] = _over

  lazy val cursor: Var[Option[PointerCursor]] = {
    val v = Var[Option[PointerCursor]](None)
    v.attach {
      case Some(cursor) if over() => Pointer.cursor @= cursor
      case None if over() => Pointer.cursor @= PointerCursor.Arrow
      case _ => // Ignore
    }
    var previous: PointerCursor = PointerCursor.Arrow
    over.attach {
      case true => v().foreach { cursor =>
        previous = Pointer.cursor()
        Pointer.cursor @= cursor
      }
      case false => v().foreach { _ =>
        Pointer.cursor @= previous
        previous = PointerCursor.Arrow
      }
    }
    v
  }
}