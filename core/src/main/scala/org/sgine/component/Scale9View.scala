package org.sgine.component

import com.badlogic.gdx.graphics.g2d.NinePatch
import org.sgine.render.RenderContext
import org.sgine.texture.Texture
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

case class Scale9(texture: Texture, ninePatch: NinePatch)

object Scale9 {
  def apply(texture: Texture, left: Int, right: Int, top: Int, bottom: Int): Scale9 = {
    val ninePatch = new NinePatch(texture.ref, left, right, top, bottom)
    Scale9(texture, ninePatch)
  }
}