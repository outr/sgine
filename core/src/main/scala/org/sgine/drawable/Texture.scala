package org.sgine.drawable

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Pixmap.Format
import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.{Color, Pixmap, Texture => GDXTexture}
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable

case class Texture(ref: TextureRegion,
                   scaleX: Double = 1.0,
                   scaleY: Double = 1.0,
                   rotation: Double = 0.0) extends Drawable {
  lazy val gdx: TextureRegionDrawable = {
    val t = new TextureRegionDrawable(ref)
    scribe.info(s"Info - Left Width:${t.getLeftWidth}, Right Width:${t.getRightWidth}, Top:${t.getTopHeight}, Bottom:${t.getBottomHeight}, Min: ${t.getMinWidth}x${t.getMinHeight}")
    t
  }

  def width: Double = ref.getRegionWidth
  def height: Double = ref.getRegionHeight

  def scaled(scale: Double): Texture = copy(scaleX = scale, scaleY = scale)
  def scaled(scaleX: Double, scaleY: Double): Texture = copy(scaleX = scaleX, scaleY = scaleY)
  def rotated(rotation: Double): Texture = copy(rotation = rotation)

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

  def internal(path: String, scaleX: Double = 1.0, scaleY: Double = 1.0, rotation: Double = 0.0): Texture = {
    val t = new GDXTexture(Gdx.files.internal(path), true)
    t.setFilter(TextureFilter.MipMap, TextureFilter.MipMap)
    Texture(new TextureRegion(t), scaleX, scaleY, rotation)
  }
}