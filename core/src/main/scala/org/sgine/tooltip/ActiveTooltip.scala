package org.sgine.tooltip

import org.sgine.component.DimensionedSupport

sealed trait ActiveTooltip {
  def isLocked: Boolean = isInstanceOf[ActiveTooltip.Locked]

  def isSet: Boolean = true
  def tooltipOption: Option[Tooltip]
  def targetOption: Option[DimensionedSupport]
}

object ActiveTooltip {
  case object None extends ActiveTooltip {
    override def isSet: Boolean = false

    override def tooltipOption: Option[Tooltip] = Option.empty

    override def targetOption: Option[DimensionedSupport] = Option.empty
  }

  sealed trait Set extends ActiveTooltip {
    def tooltip: Tooltip
    def target: DimensionedSupport

    override def tooltipOption: Option[Tooltip] = Some(tooltip)

    override def targetOption: Option[DimensionedSupport] = Some(target)
  }

  case class Hovering(tooltip: Tooltip, target: DimensionedSupport) extends Set

  case class Locked(tooltip: Tooltip, target: DimensionedSupport) extends Set
}