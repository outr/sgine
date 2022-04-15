package org.sgine.render

trait Renderable {
  def render(context: RenderContext): Unit

  def renderOrder: Double = 0.0
  def shouldRender: Boolean = true
}

object Renderable {
  implicit object ordering extends Ordering[Renderable] {
    override def compare(x: Renderable, y: Renderable): Int = y.renderOrder.compareTo(x.renderOrder)
  }
}