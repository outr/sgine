package org.sgine.util

import com.badlogic.gdx.math.{Intersector, Vector2}
import org.sgine.component.{Component, DimensionedSupport}

object MathUtils {
  def distance(x1: Double, y1: Double, x2: Double, y2: Double): Double =
    math.sqrt(math.pow(x2 - x1, 2.0) + math.pow(y2 - y1, 2.0))

  def distanceFromCenter(c1: Component, c2: Component): Double = {
    distance(
      x1 = c1.global.center,
      y1 = c1.global.middle,
      x2 = c2.global.center,
      y2 = c2.global.middle
    )
  }

  def changeLineLength(x1: Double, y1: Double, x2: Double, y2: Double, length: Double): (Double, Double) = {
    // TODO: keep the trajectory, but change the length of the line's endpoint
    (x2, y2)
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