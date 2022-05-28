package org.sgine.tooltip

import org.sgine.component.InteractiveComponent

trait TooltipSupport extends InteractiveComponent {
  def tooltip: Tooltip

  override protected def init(): Unit = {
    super.init()

    tooltip.visible := pointer.over
    Tooltip.children += tooltip
  }
}