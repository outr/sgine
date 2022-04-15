package org.sgine.component

import com.badlogic.gdx.graphics.Color
import org.sgine.render.RenderContext
import org.sgine.texture.Texture
import reactify._

class TextureView extends RenderableComponent {
  val texture: Var[Texture] = Var(Texture.Pixel)

  def this(path: String) = {
    this()
    texture @= Texture.internal(path)
  }

  texture.attachAndFire { texture =>
    width @= texture.width
    height @= texture.height
  }

  override def render(context: RenderContext): Unit = context.draw(
    ref = texture,
    x = x,
    y = y,
    scaleX = 1.0,
    scaleY = 1.0,
    rotation = 0.0,
    color = Color.WHITE,
    flipX = false,
    flipY = false
  )
}