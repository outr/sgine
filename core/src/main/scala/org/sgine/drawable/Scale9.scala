package org.sgine.drawable

import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.scenes.scene2d.utils
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable

case class Scale9(ninePatch: NinePatch) extends Drawable {
  override lazy val gdx: utils.Drawable = new NinePatchDrawable(ninePatch)

  override def width: Double = ninePatch.getTotalWidth
  override def height: Double = ninePatch.getTotalHeight
  override def scaleX: Double = 1.0
  override def scaleY: Double = 1.0
  override def rotation: Double = 0.0
}

object Scale9 {
  def apply(texture: Texture, left: Int, right: Int, top: Int, bottom: Int): Scale9 = {
    val ninePatch = new NinePatch(texture.ref, left, right, top, bottom)
    Scale9(ninePatch)
  }
  def apply(texture: Texture, horizontal: Int, vertical: Int): Scale9 =
    apply(texture, vertical, vertical, horizontal, horizontal)
  def apply(texture: Texture, value: Int): Scale9 = apply(texture, value, value)
}