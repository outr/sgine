package org.sgine.component

import org.sgine.render.RenderContext
import org.sgine.texture.Scale9
import reactify._

class Scale9View(scale9: Scale9) extends DimensionedComponent with RenderableComponent {
  override def render(context: RenderContext): Unit = {
    context.draw(
      ninePatch = scale9.ninePatch,
      transform = matrix4(context),
      width = width,
      height = height
    )
  }
}