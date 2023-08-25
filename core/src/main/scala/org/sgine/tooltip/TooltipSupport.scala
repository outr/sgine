package org.sgine.tooltip

import org.sgine.component.PointerSupport
import reactify._

trait TooltipSupport extends PointerSupport {
  lazy val tooltip: Var[Option[Tooltip]] = Var(None)

  override protected def init(): Unit = {
    super.init()

    pointer.isOver.attach {
      case true => Tooltip.active := tooltip()
      case false => Tooltip.active @= None
    }
  }
}