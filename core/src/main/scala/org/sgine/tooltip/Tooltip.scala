package org.sgine.tooltip

import org.sgine.{Overlay, Pointer}
import org.sgine.component.{AbstractContainer, Children, DimensionedSupport, MutableContainer}
import reactify._

trait Tooltip extends DimensionedSupport {
  visible @= false

  object tooltip {
    val moveWithPointer: Var[Boolean] = Var(false)
    val delay: Var[Double] = Var(1.0)
  }
}

object Tooltip extends MutableContainer[Tooltip] {
  val active: Var[Option[Tooltip]] = Var(None)

  private var countdown: Double = 0.0

  children := active().toVector

  width := screenOption().map(_.width()).getOrElse(0.0)
  height := screenOption().map(_.height()).getOrElse(0.0)

  active.changes {
    case (previous, current) =>
      previous.foreach { t =>
        t.visible @= false
        t.x @= 0.0
        t.y @= 0.0
      }
      current.foreach { t =>
        if (t.tooltip.delay() > 0.0) {
          countdown = t.tooltip.delay()
        } else {
          setTooltip(t)
        }
      }
  }

  Overlay.children += this

  private def setTooltip(t: Tooltip): Unit = {
    def positionX: Double = screenOption() match {
      case Some(screen) if Pointer.screen.x() > screen.center() => Pointer.screen.x - t.width
      case _ => Pointer.screen.x
    }

    def positionY: Double = screenOption() match {
      case Some(screen) if Pointer.screen.y() > screen.middle() => Pointer.screen.y - t.height
      case _ => Pointer.screen.y
    }

    if (t.tooltip.moveWithPointer()) {
      t.left := positionX
      t.top := positionY
    } else {
      t.left @= positionX
      t.top @= positionY
    }

    t.visible @= true
  }

  override def update(delta: Double): Unit = {
    super.update(delta)

    if (countdown > 0.0) {
      countdown -= delta

      if (countdown <= 0.0) {
        countdown = 0.0
        active().foreach(setTooltip)
      }
    }
  }
}