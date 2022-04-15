package org.sgine.texture

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.{Color, Pixmap, Texture => GDXTexture}
import com.badlogic.gdx.graphics.Pixmap.Format
import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.graphics.g2d.TextureRegion

case class Texture(ref: TextureRegion,
                   scaleX: Double = 1.0,
                   scaleY: Double = 1.0,
                   rotation: Double = 0.0) {
  def width: Double = ref.getRegionWidth
  def height: Double = ref.getRegionHeight

  override def toString: String = s"TextureReference($ref, $width, $height)"
}

object Texture {
  lazy val Pixel: Texture = {
    val pixmap = new Pixmap(1, 1, Format.RGBA8888)
    pixmap.setColor(Color.WHITE)
    pixmap.drawPixel(0, 0)
    val texture = new GDXTexture(pixmap)
    pixmap.dispose()
    val region = new TextureRegion(texture, 0, 0, 1, 1)
    Texture(region)
  }

  def internal(path: String): Texture = {
    val t = new GDXTexture(Gdx.files.internal(path), true)
    t.setFilter(TextureFilter.MipMap, TextureFilter.MipMap)
    Texture(new TextureRegion(t))
  }
}