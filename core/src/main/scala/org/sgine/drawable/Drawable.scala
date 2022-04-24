package org.sgine.drawable

import com.badlogic.gdx.scenes.scene2d.utils.{Drawable => GDXDrawable}

trait Drawable {
  def gdx: GDXDrawable
  def width: Double
  def height: Double
  def scaleX: Double
  def scaleY: Double
  def rotation: Double
  def scaledWidth: Double = width * scaleX
  def scaledHeight: Double = height * scaleY
}