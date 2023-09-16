package org.sgine.tooltip

sealed trait ActiveTooltip {
  def isLocked: Boolean = isInstanceOf[ActiveTooltip.Locked]

  def toOption: Option[Tooltip]
}

object ActiveTooltip {
  case object None extends ActiveTooltip {
    override def toOption: Option[Tooltip] = Option.empty
  }

  case class Hovering(tooltip: Tooltip) extends ActiveTooltip {
    override lazy val toOption: Option[Tooltip] = Some(tooltip)
  }

  case class Locked(tooltip: Tooltip) extends ActiveTooltip {
    override lazy val toOption: Option[Tooltip] = Some(tooltip)
  }
}