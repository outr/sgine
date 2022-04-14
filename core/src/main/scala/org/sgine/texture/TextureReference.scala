package org.sgine.texture

import com.badlogic.gdx.graphics.g2d.TextureRegion

case class TextureReference(ref: TextureRegion,
                            scaleX: Double = 1.0,
                            scaleY: Double = 1.0,
                            rotation: Double = 0.0) {
  def width: Double = ref.getRegionWidth
  def height: Double = ref.getRegionHeight

  override def toString: String = s"TextureReference($ref, $width, $height)"
}