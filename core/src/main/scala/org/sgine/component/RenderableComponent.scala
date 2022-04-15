package org.sgine.component

import org.sgine.render.Renderable

trait RenderableComponent extends DimensionedComponent with Renderable {
  override def renderOrder: Double = z()

  override def shouldRender: Boolean = isVisible()
}
