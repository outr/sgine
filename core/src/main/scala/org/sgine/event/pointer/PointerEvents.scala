package org.sgine.event.pointer

import org.sgine.{Pointer, PointerCursor}
import reactify._

class PointerEvents {
  lazy val enabled: Var[Boolean] = Var(true)
  lazy val draggable: Var[Boolean] = Var(false)

  lazy val down: Channel[PointerDownEvent] = Channel[PointerDownEvent]
  lazy val dragged: Channel[PointerDraggedEvent] = Channel[PointerDraggedEvent]
  lazy val moved: Channel[PointerMovedEvent] = Channel[PointerMovedEvent]
  lazy val up: Channel[PointerUpEvent] = Channel[PointerUpEvent]
  lazy val over: Channel[PointerOverEvent] = Channel[PointerOverEvent]
  lazy val click: Channel[PointerUpEvent] = Channel[PointerUpEvent]

  private lazy val pressed: Var[Boolean] = {
    val v = Var(false)
    down.on(v @= true)
    isOver.attach { b =>
      if (!b) v @= false
    }
    up.attach { evt =>
      if (v()) {
        click @= evt
      }
      v @= false
    }
    v
  }

  lazy val isOver: Val[Boolean] = Var(false)
  lazy val isPressed: Val[Boolean] = Var(pressed)

  lazy val cursor: Var[Option[PointerCursor]] = {
    val v = Var[Option[PointerCursor]](None)
    v.attach {
      case Some(cursor) if isOver() => Pointer.cursor @= cursor
      case None if isOver() => Pointer.cursor @= PointerCursor.Arrow
      case _ => // Ignore
    }
    var previous: PointerCursor = PointerCursor.Arrow
    isOver.attach {
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