package org.sgine.event

import org.sgine.component.{DimensionedSupport, PointerSupport}
import org.sgine.event.pointer.PointerDraggedEvent
import reactify._
import reactify.reaction.Reaction

trait DraggableSupport {
  val draggable: Var[Option[Draggable]] = Var(None)
  object drag {
    def boundTo(container: DimensionedSupport): Unit = {
      drag.min.x := Some(container.x)
      drag.min.y := Some(container.y)
      drag.max.x := Some(container.width)
      drag.max.y := Some(container.height)
    }
    def within(container: DimensionedSupport,
               padLeft: => Double = 0.0,
               padTop: => Double = 0.0,
               padRight: => Double = 0.0,
               padBottom: => Double = 0.0): Unit = {
      val d = draggable().getOrElse(throw new RuntimeException("Draggable must be set before calling within."))
      val t = d.dragTarget
      drag.min.x := Some(container.width - t.width - padRight)
      drag.min.y := Some(container.height - t.height - padBottom)
      drag.max.x := Some(t.width + padLeft)
      drag.max.y := Some(t.height + padTop)
    }
    def unbound(): Unit = {
      drag.min.x @= None
      drag.min.y @= None
      drag.max.x @= None
      drag.max.y @= None
    }

    object min {
      lazy val x: Var[Option[Double]] = Var(None)
      lazy val y: Var[Option[Double]] = Var(None)
    }
    object max {
      lazy val x: Var[Option[Double]] = Var(None)
      lazy val y: Var[Option[Double]] = Var(None)
    }
  }

  private val reaction = Reaction[PointerDraggedEvent] { evt =>
    draggable.foreach { d =>
      var destX = d.dragTarget.x + evt.deltaX
      var destY = d.dragTarget.y + evt.deltaY
      drag.min.x.foreach { min =>
        destX = math.max(destX, min)
      }
      drag.min.y.foreach { min =>
        destY = math.max(destY, min)
      }
      drag.max.x.foreach { max =>
        destX = math.min(destX, max - d.dragTarget.width)
      }
      drag.max.y.foreach { max =>
        destY = math.min(destY, max - d.dragTarget.height)
      }
      d.dragTarget.x @= destX
      d.dragTarget.y @= destY
    }
  }

  draggable.changes {
    case (oldValue, newValue) =>
      oldValue.foreach { d =>
        d.dragSource.pointer.dragged.reactions -= reaction
      }
      newValue.foreach { d =>
        d.dragSource.pointer.draggable @= true
        d.dragSource.pointer.dragged.reactions += reaction
      }
  }
}

case class Draggable(dragSource: PointerSupport, dragTarget: DimensionedSupport)