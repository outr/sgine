package org.sgine.util

import com.badlogic.gdx.math.{Intersector, Vector2}
import org.sgine.component.Component

object MathUtils {
  private lazy val t1: Vector2 = new Vector2()
  private lazy val t2: Vector2 = new Vector2()
  private lazy val t3: Vector2 = new Vector2()

  def degreesToRadians(degrees: Double): Double = degrees * math.Pi / 180.0

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
    val currentLength = math.sqrt(math.pow(x2 - x1, 2.0) + math.pow(y2 - y1, 2.0))
    val newLength = length - currentLength
    val nx = x2 + (x2 - x1) / currentLength * newLength
    val ny = y2 + (y2 - y1) / currentLength * newLength
    (nx, ny)
  }

  def intersectSegmentCircle(x1: Double,
                             y1: Double,
                             x2: Double,
                             y2: Double,
                             circleX: Double,
                             circleY: Double,
                             circleRadius: Double): Boolean = {
    t1.x = x1.toFloat
    t1.y = y1.toFloat
    t2.x = x2.toFloat
    t2.y = y2.toFloat
    t3.x = circleX.toFloat
    t3.y = circleY.toFloat
    Intersector.intersectSegmentCircle(t1, t2, t3, math.pow(circleRadius, 2.0).toFloat)
  }
}