package org.sgine.component

import reactify._

trait DimensionedComponent extends Component {
  lazy val x: Var[Double] = Var(0.0)
  lazy val y: Var[Double] = Var(0.0)
  lazy val z: Var[Int] = Var(0)

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

  def depth: Var[Int] = z

//  protected val parentDimensioned: Val[Option[DimensionedComponent]] = Val(parent().flatMap(c => parentDimensionedFor(c)))
//  protected val parentLastCalculated: Val[Long] = Val(parentDimensioned().map(_.lastCalculated()).getOrElse(0L))

  /*private val _lastCalculated = Var[Long](0L)
  private val _matrix4 = new Matrix4()
  private var recalculate = true

  def lastCalculated: Val[Long] = _lastCalculated

  x.and(y).and(z).and(width).and(height).and(rotation).on {
    recalculate = true
    _lastCalculated @= System.currentTimeMillis()
  }
  parentDimensioned.on {
    recalculate = true
    _lastCalculated @= System.currentTimeMillis()
  }
  parentLastCalculated.on {
    recalculate = true
    _lastCalculated @= System.currentTimeMillis()
  }

  this match {
    case c: TypedContainer[_] =>
      width := c.children().foldLeft(0.0)((max, child) => child match {
        case dc: DimensionedComponent => math.max(max, dc.x + dc.width)
        case _ => max
      })
      height := c.children().foldLeft(0.0)((max, child) => child match {
        case dc: DimensionedComponent => math.max(max, dc.y + dc.height)
        case _ => max
      })
    case _ => // Ignore
  }

  protected[sgine] def matrix4(context: RenderContext, main: Boolean = true): Matrix4 = {
    if (recalculate) {
      val originX = (width / 2.0).toFloat
      val originY = (height / 2.0).toFloat
      val originZ = 0.0f
      val x = this.x.toFloat
      val y = if (main) (-this.y.toFloat + context.screen.height - height).toFloat else -this.y.toFloat
      val sx = scaleX.toFloat
      val sy = scaleY.toFloat
      parentDimensioned() match {
        case Some(p) =>
          _matrix4.set(p.matrix4(context, main = false))
        case None => _matrix4.idt()
      }
      _matrix4
        .translate(x, y, 0.0f)
        .translate(originX, originY, originZ)
        .rotate(0.0f, 0.0f, 1.0f, -rotation().toFloat)
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

  protected[sgine] def updateHitVector(v: Vector3): Unit = {
    parentDimensioned.foreach(p => p.updateHitVector(v))
    val originX = (width / 2.0).toFloat
    val originY = (height / 2.0).toFloat
    v.add(-x.toFloat, -y.toFloat, 0.0f)
    v.add(-originX, -originY, 0.0f)
    v.rotate(-rotation.toFloat, 0.0f, 0.0f, 1.0f)
    v.add(originX, originY, 0.0f)
  }*/
}