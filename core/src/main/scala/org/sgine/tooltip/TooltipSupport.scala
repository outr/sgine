package org.sgine.tooltip

import org.sgine.component.PointerSupport
import reactify._

trait TooltipSupport extends PointerSupport {
  lazy val tooltip: Var[Option[Tooltip]] = Var(None)

  override protected def init(): Unit = {
    super.init()

    def setTooltip(t: Tooltip): Unit = {
      t.visible := pointer.isOver
      Tooltip.children += t
    }

    tooltip().foreach(setTooltip)

    tooltip.changes {
      case (previous, current) =>
        previous.foreach { t =>
          t.visible @= false
          Tooltip.children -= t
        }
        current.foreach(setTooltip)
    }
  }
}