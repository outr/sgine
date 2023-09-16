package org.sgine.tooltip

import org.sgine.{Overlay, Pointer}
import org.sgine.component.{AbstractContainer, Children, DimensionedSupport, MutableContainer}
import reactify._

trait Tooltip extends DimensionedSupport {
  visible @= false

  object tooltip {
    val moveWithPointer: Var[Boolean] = Var(true)
    val delay: Var[Double] = Var(1.0)
  }
}

object Tooltip extends MutableContainer[Tooltip] {
  val active: Var[ActiveTooltip] = Var(ActiveTooltip.None)

  def isLocked: Boolean = active.isLocked

  private var countdown: Double = 0.0
  private var localX: Double = 0.0
  private var localY: Double = 0.0

  children := active.tooltipOption.toVector

  width := screenOption().map(_.width()).getOrElse(0.0)
  height := screenOption().map(_.height()).getOrElse(0.0)

  active.changes {
    case (previous, current) =>
      previous.tooltipOption.foreach { t =>
        t.visible @= false
        t.x @= 0.0
        t.y @= 0.0
      }
      current match {
        case at: ActiveTooltip.Set =>
          val t = at.tooltip
          if (t.tooltip.delay() > 0.0 && !current.isLocked) {
            countdown = t.tooltip.delay()
          } else {
            countdown = 0.0
            setTooltip(at, changed = true)
          }
        case _ => // Ignore
      }
  }

  Overlay.children += this

  private def setTooltip(at: ActiveTooltip.Set, changed: Boolean): Unit = {
    if (changed) {
      localX = at.target.global.localizeX(Pointer.screen.x())
      localY = at.target.global.localizeY(Pointer.screen.y())
    }

    def positionX: Double = {
      var globalX = Pointer.screen.x()
      if (!at.tooltip.tooltip.moveWithPointer() || at.isLocked) {
        globalX = at.target.global.x + localX
      }
      screenOption() match {
        case Some(screen) if globalX > screen.center() => globalX - at.tooltip.width
        case _ => globalX
      }
    }

    def positionY: Double = {
      var globalY = Pointer.screen.y()
      if (!at.tooltip.tooltip.moveWithPointer() || at.isLocked) {
        globalY = at.target.global.y + localY
      }
      screenOption() match {
        case Some(screen) if globalY > screen.middle() => globalY - at.tooltip.height
        case _ => globalY
      }
    }

    at.tooltip.left := positionX
    at.tooltip.top := positionY

    at.tooltip.visible @= true
  }

  def hide(): Unit = active @= ActiveTooltip.None

  override def update(delta: Double): Unit = {
    super.update(delta)

    if (countdown > 0.0) {
      countdown -= delta

      if (countdown <= 0.0) {
        countdown = 0.0
        active() match {
          case at: ActiveTooltip.Set => setTooltip(at, changed = false)
          case _ => // Ignore
        }
      }
    }
  }
}