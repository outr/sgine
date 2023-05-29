package org.sgine.layout

import org.sgine.component.{AbstractContainer, DimensionedSupport}
import reactify._

class GridLayout private() extends InvalidatingLayoutManager {
  def this(columns: Int) = {
    this()
    this.columns @= columns
  }

  val columns: Var[Int] = Var(2)
  val xPad: Var[Double] = Var(0.0)
  val yPad: Var[Double] = Var(0.0)
  val startX: Var[Double] = Var(0.0)
  val startY: Var[Double] = Var(0.0)

  columns.on(invalidate())
  xPad.on(invalidate())
  yPad.on(invalidate())
  startX.on(invalidate())
  startY.on(invalidate())

  override def validate(container: AbstractContainer): Unit = {
    val rows = container
      .children
      .collect {
        case c: DimensionedSupport if c.includeInLayout() => c
      }
      .grouped(columns)
      .toVector
    rows.zipWithIndex.foreach {
      case (row, yIndex) => row.zipWithIndex.foreach {
        case (child, xIndex) =>
          child.left := (if (xIndex == 0) startX else row(xIndex - 1).right + xPad)
          child.top := (if (yIndex == 0) startY else rows(yIndex - 1).map(_.bottom()).max + yPad)
      }
    }
  }
}
