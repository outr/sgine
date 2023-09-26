package org.sgine.drawable

import com.badlogic.gdx.math.Vector2
import org.sgine.Color
import org.sgine.util.MathUtils
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
  private var _color: Color = Color.White
  private var _alpha: Double = 1.0

  var rotation: Double = 0.0
  def color: Color = _color
  def color_=(color: Color): Unit = updateColor(color, alpha)
  def alpha: Double = _alpha
  def alpha_=(alpha: Double): Unit = updateColor(color, alpha)
  var joinType: JoinType = JoinType.SMOOTH

  def baseX: Double = _baseX
  def baseY: Double = _baseY
  def width: Double = _width
  def height: Double = _height

  private def preDraw(): Unit = {
    shapeDrawer.setColor(color.gdx)
  }

  private def updateColor(color: Color, alpha: Double): Unit = {
    _color = color.withAlpha(color.alpha * alpha)
    _alpha = alpha
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

  def circle(x: Double = 0.0, y: Double = 0.0, radius: Double, lineWidth: Double): Unit = {
    preDraw()
    shapeDrawer.circle(x.cx, y.cy, radius.f, lineWidth.f, joinType)
  }

  private lazy val v = new Vector2
  private lazy val v2 = new Vector2

  def line(x1: Double,
           y1: Double,
           x2: Double,
           y2: Double,
           lineWidth: Double,
           snap: Boolean = false,
           color1: Color = color,
           color2: Color = color): Unit = {
    preDraw()
    val (x1f, y1f) = fix(x1, y1)
    val (x2f, y2f) = fix(x2, y2)
    shapeDrawer.line(x1f, y1f, x2f, y2f, lineWidth.f, snap, color1.gdx.toFloatBits, color2.gdx.toFloatBits)
  }

  object filled {
    def rectangle(x: Double = 0.0, y: Double = 0.0, width: Double = width, height: Double = height): Unit = {
      preDraw()
      shapeDrawer.filledRectangle(x.cx, y.cy, width.f, -height.f, rotation.f)
    }

    def circle(x: Double, y: Double, radius: Double): Unit = {
      preDraw()
      shapeDrawer.filledCircle(x.cx, y.cy, radius.f)
    }

    def sector(x: Double,
               y: Double,
               radius: Double,
               startAngle: Double,
               degrees: Double,
               innerColor: Color,
               outerColor: Color): Unit = {
      preDraw()

      shapeDrawer.sector(
        x.cx,
        y.cy,
        radius.f,
        MathUtils.degreesToRadians(90.0 - startAngle - degrees).f,
        MathUtils.degreesToRadians(degrees).f,
        innerColor.gdx,
        outerColor.gdx
      )
    }
  }

  // TODO: Figure out a way to apply transform
  def draw(texture: Texture)
          (x: Double = 0.0, y: Double = 0.0,
           originX: Double = texture.width / 2.0, originY: Double = texture.height / 2.0,
           width: Double = texture.width, height: Double = texture.height,
           scaleX: Double = 1.0, scaleY: Double = 1.0,
           rotation: Double = 0.0): Unit = {
    preDraw()
    shapeDrawer.getBatch.draw(
      texture.ref,
      x.cx, y.cy,
      originX.f, -originY.f,
      width.f, -height.f,
      scaleX.toFloat, scaleY.toFloat,
      rotation.f
    )
  }

  def reset(x: Double, y: Double, width: Double, height: Double, color: Color, rotation: Double, alpha: Double): Unit = {
    _baseX = x
    _baseY = y
    _width = width
    _height = height
    this.color = color
    this.rotation = MathUtils.degreesToRadians(rotation)
    this.alpha = alpha
  }
}
