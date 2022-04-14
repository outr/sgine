package org.sgine.render

import reactify.Val

trait Renderable {
  def depth: Val[Double]

  def render(context: RenderContext): Unit

  def shouldRender: Boolean
}

class RenderContext {
  // TODO: implement, includes delta
}