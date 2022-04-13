package org.sgine.render

import org.sgine.component.Component
import reactify.Val

trait Renderable extends Component {
  def depth: Val[Double]

  def render(context: RenderContext): Unit

  def shouldRender: Boolean
}

class RenderContext {
  // TODO: implement, includes delta
}