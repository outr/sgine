package org.sgine.render

import reactify.{Val, Var}

trait Renderable {
  lazy val depth: Val[Double] = Var(0.0)

  def render(context: RenderContext): Unit

  def shouldRender: Boolean = true
}

object Renderable {
  implicit object ordering extends Ordering[Renderable] {
    override def compare(x: Renderable, y: Renderable): Int = y.depth().compareTo(x.depth())
  }
}