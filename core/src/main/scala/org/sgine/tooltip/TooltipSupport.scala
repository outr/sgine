package org.sgine.tooltip

import org.sgine.component.PointerSupport

trait TooltipSupport extends PointerSupport {
  def tooltip: Tooltip

  override protected def init(): Unit = {
    super.init()

    tooltip.visible := pointer.over
    Tooltip.children += tooltip
  }
}