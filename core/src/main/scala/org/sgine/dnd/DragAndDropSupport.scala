package org.sgine.dnd

import org.sgine.component.{DimensionedSupport, PointerSupport}
import org.sgine.{Overlay, Pointer}

import reactify._

trait DragAndDropSupport extends DimensionedSupport with PointerSupport {
  type Drag <: DimensionedSupport

  private var dragging: Option[Drag] = None

  private var current: Option[(DropSupport, Boolean)] = None

  pointer.draggable @= true
  pointer.dragged.attach { evt =>
    if (dragging.isEmpty) {
      val c = createDragComponent()
      c.center := Pointer.screen.x
      c.middle := Pointer.screen.y
      Overlay.children += c
      dragging = Some(c)
    }
    val drag = dragging.get
    val previous = current
    val screen = screenOption().get
    current = screen.hitsAtPointer().collectFirst {
      case drop: DropSupport => (drop, drop.accepts(this))
    }
    if (current != previous) {
      previous.foreach {
        case (drop, accept) =>
          drop.out(this, accept)
          out(drag, drop, accept)
      }
      current.foreach {
        case (drop, accept) =>
          drop.over(this, accept)
          over(drag, drop, accept)
      }
    }
    if (evt.finished) {
      current.foreach {
        case (drop, accept) =>
          drop.receive(this, accept)
          receive(drag, drop, accept)
          current = None
      }

      drag.removeFromParent()
      dragging = None
    }
  }

  protected def createDragComponent(): Drag

  protected def out(drag: Drag, drop: DropSupport, accept: Boolean): Unit = {}

  protected def over(drag: Drag, drop: DropSupport, accept: Boolean): Unit = {}

  protected def receive(drag: Drag, drop: DropSupport, accept: Boolean): Unit = {}
}
