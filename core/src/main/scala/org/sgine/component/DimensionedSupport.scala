package org.sgine.component

import org.sgine.{Dimension, Screen}
import reactify._

trait DimensionedSupport extends Component {
  lazy val x: Var[Double] = Var(0.0)
  lazy val y: Var[Double] = Var(0.0)
  lazy val z: Var[Int] = Var(0)

  lazy val width: Dimension = new Dimension
  lazy val height: Dimension = new Dimension

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

  override def screenX: Double = super.screenX + x()
  override def screenY: Double = super.screenY + y()

  override protected def init(): Unit = {
    super.init()

    this match {
      case _: Screen => // Ignore Screen
      case c: TypedContainer[_] =>

        width.preferred := c.children().foldLeft(0.0)((max, child) => child match {
          case dc: DimensionedSupport => math.max(max, dc.x + dc.width)
          case _ => max
        })
        height.preferred := c.children().foldLeft(0.0)((max, child) => child match {
          case dc: DimensionedSupport => math.max(max, dc.y + dc.height)
          case _ => max
        })
      case _ => // Ignore
    }
  }
}