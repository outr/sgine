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

  def this(texture: Texture) = {
    this()
    this.texture @= texture
  }

  texture.attachAndFire { texture =>
    width := texture.scaledWidth * scaleX
    height := texture.scaledHeight * scaleY
  }

  override def render(context: RenderContext): Unit = context.draw(
    texture = texture,
    transform = matrix4(context),
    color = color
  )
}