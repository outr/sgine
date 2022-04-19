package org.sgine.component

import org.sgine.Color
import org.sgine.render.RenderContext
import org.sgine.texture.Texture
import reactify._

class TextureView extends RenderableComponent {
  val texture: Var[Texture] = Var(Texture.Pixel)
  val color: Var[Color] = Var(Color.White)

  def this(path: String) = {
    this()
    texture @= Texture.internal(path)
  }

  texture.attachAndFire { texture =>
    width := texture.width * scaleX
    height := texture.height * scaleY
  }

  override def render(context: RenderContext): Unit = context.draw(
    texture = texture,
    transform = matrix4(context),
    color = color
  )
}