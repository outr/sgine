package org.sgine.layout

import org.sgine.Compass
import org.sgine.component.{AbstractContainer, DimensionedSupport}
import reactify._

class BoxLayout(direction: Compass = Compass.South,
                spacing: => Double = 0.0,
                offset: => Double = 0.0) extends InvalidatingLayoutManager {
  private def anchor(c: DimensionedSupport): Mutable[Double] = direction match {
    case Compass.North => c.bottom
    case Compass.South => c.top
    case Compass.East => c.right
    case Compass.West => c.left
  }

  private def prev(c: DimensionedSupport): Double = direction match {
    case Compass.North => c.top - spacing
    case Compass.South => c.bottom + spacing
    case Compass.East => c.left - spacing
    case Compass.West => c.right + spacing
  }

  private def start(container: AbstractContainer): Double = direction match {
    case Compass.North => container.height - offset
    case Compass.South => offset
    case Compass.East => container.width - offset
    case Compass.West => offset
  }

  override def validate(container: AbstractContainer): Unit = {
    var previous: Option[DimensionedSupport] = None
    container.children.foreach {
      case c: DimensionedSupport if c.includeInLayout() =>
        previous match {
          case Some(p) => anchor(c) := prev(p)
          case None => anchor(c) := start(container)
        }
        previous = Some(c)
      case _ => // Ignore
    }
  }
}