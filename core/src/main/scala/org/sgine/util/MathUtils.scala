package org.sgine.util

import com.badlogic.gdx.math.{Intersector, Vector2}
import org.sgine.component.{Component, DimensionedSupport}

object MathUtils {
  def distance(x1: Double, y1: Double, x2: Double, y2: Double): Double =
    math.sqrt(math.pow(x2 - x1, 2.0) + math.pow(y2 - y1, 2.0))

  def distanceFromCenter(c1: Component, c2: Component): Double = {
    val (c1Center, c1Middle) = c1 match {
      case ds: DimensionedSupport => ds.center() -> ds.middle()
      case _ => 0.0 -> 0.0
    }
    val (c2Center, c2Middle) = c2 match {
      case ds: DimensionedSupport => ds.center() -> ds.middle()
      case _ => 0.0 -> 0.0
    }
    distance(
      x1 = c1.screenX + c1Center,
      y1 = c1.screenY + c1Middle,
      x2 = c2.screenX + c2Center,
      y2 = c2.screenY + c2Middle
    )
  }

  private lazy val start: Vector2 = new Vector2()
  private lazy val end: Vector2 = new Vector2()
  private lazy val center: Vector2 = new Vector2()

  def intersectSegmentCircle(x1: Double,
                             y1: Double,
                             x2: Double,
                             y2: Double,
                             circleX: Double,
                             circleY: Double,
                             circleRadius: Double): Boolean = {
    start.x = x1.toFloat
    start.y = y1.toFloat
    end.x = x2.toFloat
    end.y = y2.toFloat
    center.x = circleX.toFloat
    center.y = circleY.toFloat
    Intersector.intersectSegmentCircle(start, end, center, circleRadius.toFloat)
  }
}