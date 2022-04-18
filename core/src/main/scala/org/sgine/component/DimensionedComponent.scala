package org.sgine.component

import com.badlogic.gdx.math.Matrix4
import org.sgine.render.RenderContext
import reactify._

trait DimensionedComponent extends Component {
  lazy val x: Var[Double] = Var(0.0)
  lazy val y: Var[Double] = Var(0.0)
  lazy val z: Var[Double] = Var(0.0)

  lazy val width: Var[Double] = Var(0.0)
  lazy val height: Var[Double] = Var(0.0)

  lazy val scaleX: Var[Double] = Var(1.0)
  lazy val scaleY: Var[Double] = Var(1.0)
  lazy val rotation: Var[Double] = Var(0.0)

  def left: Var[Double] = x
  lazy val center: Dep[Double, Double] = Dep(left)(_ + (width / 2.0), _ - (width / 2.0))
  lazy val right: Dep[Double, Double] = Dep(left)(_ + width, _ - width)

  def top: Var[Double] = y
  lazy val middle: Dep[Double, Double] = Dep(top)(_ + (height / 2.0), _ - (height / 2.0))
  lazy val bottom: Dep[Double, Double] = Dep(top)(_ + height, _ - height)

  def depth: Var[Double] = z

  private val _matrix4 = new Matrix4()
  private var recalculate = true

  x.and(y).and(z).and(width).and(height).and(rotation).on {
    recalculate = true
  }

  protected def matrix4(context: RenderContext): Matrix4 = {
    if (recalculate) {
      val originX = (width / 2.0).toFloat
      val originY = (height / 2.0).toFloat
      val originZ = 0.0f
      val x = this.x.toFloat
      val y = (-this.y.toFloat + context.screen.height - (height * scaleY)).toFloat
      val sx = scaleX.toFloat
      val sy = scaleY.toFloat
      _matrix4
        .idt()    // TODO: apply world matrix
        .translate(x, y, 0.0f)
        .translate(originX, originY, originZ)
        .rotate(0.0f, 0.0f, 1.0f, rotation().toFloat)
        .scale(sx, sy, 1.0f)
        .translate(-originX / sx, -originY / sy, -originZ)

      recalculate = false
    }
    _matrix4
  }
}