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

  private val parentDimensioned: Val[Option[DimensionedComponent]] = Val(parent().flatMap(c => parentDimensionedFor(c)))
  private val parentLastCalculated: Val[Long] = Val(parentDimensioned().map(_.lastCalculated()).getOrElse(0L))

  private val _lastCalculated = Var[Long](0L)
  private val _matrix4 = new Matrix4()
  private var recalculate = true

  def lastCalculated: Val[Long] = _lastCalculated

  x.and(y).and(z).and(width).and(height).and(rotation).on {
    recalculate = true
  }
  parentDimensioned.on {
    recalculate = true
  }
  parentLastCalculated.on {
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
      parentDimensioned() match {
        case Some(p) =>
          _matrix4.set(p.matrix4(context))
          _matrix4.translate(0.0f, -context.screen.height.toFloat, 0.0f)
        case None => _matrix4.idt()
      }
      _matrix4
        .translate(x, y, 0.0f)
        .translate(originX, originY, originZ)
        .rotate(0.0f, 0.0f, 1.0f, rotation().toFloat)
        .scale(sx, sy, 1.0f)
        .translate(-originX / sx, -originY / sy, -originZ)
      _lastCalculated @= System.currentTimeMillis()
      recalculate = false
    }
    _matrix4
  }

  private def parentDimensionedFor(component: Component): Option[DimensionedComponent] = component match {
    case dc: DimensionedComponent => Some(dc)
    case _ => component.parent().flatMap(parentDimensionedFor)
  }
}