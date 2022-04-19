package org.sgine.texture

import com.badlogic.gdx.graphics.g2d.NinePatch

case class Scale9(texture: Texture, ninePatch: NinePatch)

object Scale9 {
  def apply(texture: Texture, left: Int, right: Int, top: Int, bottom: Int): Scale9 = {
    val ninePatch = new NinePatch(texture.ref, left, right, top, bottom)
    Scale9(texture, ninePatch)
  }
}