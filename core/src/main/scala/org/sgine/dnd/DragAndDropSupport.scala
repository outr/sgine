package org.sgine.dnd

import org.sgine.component.{DimensionedSupport, PointerSupport}
import org.sgine.{Overlay, Pointer}

import reactify._

trait DragAndDropSupport extends DimensionedSupport with PointerSupport {
  private var dragging: Option[DimensionedSupport] = None

  private var current: Option[DropSupport] = None

  pointer.draggable @= true
  pointer.dragged.attach { evt =>
    if (dragging.isEmpty) {
      val c = createDragComponent()
      c.center := Pointer.screen.x
      c.middle := Pointer.screen.y
      Overlay.children += c
      dragging = Some(c)
    }
    val d = dragging.get
    val previous = current
    val screen = screenOption().get
    current = screen.hitsAtPointer().collectFirst {
      case drop: DropSupport if drop.accepts(this) => drop
    }
    if (current != previous) {
      previous.foreach { drop =>
        drop.out(this)
      }
      current.foreach { drop =>
        drop.over(this)
      }
    }
    if (evt.finished) {
      current.foreach { drop =>
        drop.receive(this)
        current = None
      }

      d.removeFromParent()
      dragging = None
    }
  }

  protected def createDragComponent(): DimensionedSupport
}
