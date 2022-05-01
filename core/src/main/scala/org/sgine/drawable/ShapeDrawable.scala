package org.sgine.drawable

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.utils
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable
import space.earlygrey.shapedrawer.ShapeDrawer

trait ShapeDrawable extends Drawable { self =>
  private var shapeDrawer: ShapeDrawer = _

  override def scaleX: Double = 1.0

  override def scaleY: Double = 1.0

  override def rotation: Double = 0.0

  // TODO: Create ShapeDrawer abstraction with translation
  def draw(drawer: ShapeDrawer, x: Double, y: Double): Unit

  override lazy val gdx: utils.Drawable = new BaseDrawable {
    override def draw(batch: Batch, x: Float, y: Float, width: Float, height: Float): Unit = {
      if (shapeDrawer == null || shapeDrawer.getBatch != batch) {
        shapeDrawer = new ShapeDrawer(batch, Texture.Pixel.ref)
      }
      self.draw(shapeDrawer, x.toDouble, y.toDouble)
    }
  }
}