package org.sgine.drawable

import com.badlogic.gdx.math.{MathUtils, Vector2}
import org.sgine.Color
import space.earlygrey.shapedrawer.{JoinType, ShapeDrawer}

/**
 * Used by ShapeDrawable to draw
 *
 * @param shapeDrawer the underlying ShapeDrawer to use
 */
case class Drawer(shapeDrawer: ShapeDrawer) {
  private var _baseX: Double = 0.0
  private var _baseY: Double = 0.0
  private var _width: Double = 0.0
  private var _height: Double = 0.0

  var rotation: Double = 0.0
  var color: Color = Color.White
  var joinType: JoinType = JoinType.SMOOTH

  def baseX: Double = _baseX
  def baseY: Double = _baseY
  def width: Double = _width
  def height: Double = _height

  private def preDraw(): Unit = {
    shapeDrawer.setColor(color.gdx)
  }

  private implicit class DoubleConversions(d: Double) {
    def cx: Float = (baseX + d).toFloat
    def cy: Float = ((baseY + height) - d).toFloat
    def f: Float = d.toFloat
  }

  private def fix(x: Double, y: Double): (Float, Float) = {
    v2.set((width / 2.0).cx, (height / 2.0).cy)
    v.set(x.cx, y.cy)
    v.rotateAroundRad(v2, rotation.toFloat)
    (v.x, v.y)
  }

  def rectangle(x: Double = 0.0, y: Double = 0.0, width: Double = width, height: Double = height, lineWidth: Double): Unit = {
    preDraw()
    shapeDrawer.rectangle(x.cx, y.cy, width.f, -height.f, lineWidth.f, rotation.f, joinType)
  }

  private lazy val v = new Vector2
  private lazy val v2 = new Vector2

  def line(x1: Double, y1: Double, x2: Double, y2: Double, lineWidth: Double): Unit = {
    preDraw()
    val (x1f, y1f) = fix(x1, y1)
    val (x2f, y2f) = fix(x2, y2)
    shapeDrawer.line(x1f, y1f, x2f, y2f, lineWidth.f)
  }

  object filled {
    def rectangle(x: Double = 0.0, y: Double = 0.0, width: Double = width, height: Double = height): Unit = {
      preDraw()
      shapeDrawer.filledRectangle(x.cx, y.cy, width.f, -height.f, rotation.f)
    }
  }

  def reset(x: Double, y: Double, width: Double, height: Double, color: Color, rotation: Double): Unit = {
    _baseX = x
    _baseY = y
    _width = width
    _height = height
    this.color = color
    this.rotation = rotation * MathUtils.degreesToRadians
  }
}
