package org.sgine.tooltip

import org.sgine.component.PointerSupport
import reactify._

trait TooltipSupport extends PointerSupport {
  object tooltip extends Var[Option[Tooltip]](None) {
    lazy val clickLock: Var[Boolean] = Var(false)
  }

  override protected def init(): Unit = {
    super.init()

    pointer.isOver.attach { b =>
      if (!Tooltip.isLocked) {
        if (b) {
          tooltip.foreach { t =>
            Tooltip.active := ActiveTooltip.Hovering(t)
          }
        } else {
          Tooltip.active @= ActiveTooltip.None
        }
      }
    }
    pointer.down.on {
      if (tooltip.clickLock()) {
        tooltip.foreach { t =>
          val at = ActiveTooltip.Locked(t)
          if (Tooltip.active() == at) {
            Tooltip.active @= ActiveTooltip.None
          } else {
            Tooltip.active @= at
          }
        }
      }
    }
  }
}