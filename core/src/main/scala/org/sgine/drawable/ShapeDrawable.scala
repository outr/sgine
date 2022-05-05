package org.sgine.drawable

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.utils
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable
import org.sgine.Color
import space.earlygrey.shapedrawer.ShapeDrawer
import space.earlygrey.shapedrawer.scene2d.ShapeDrawerDrawable

trait ShapeDrawable extends Drawable { self =>
  private var drawer: Drawer = _
  private var _rotation: Double = 0.0

  override def scaleX: Double = 1.0

  override def scaleY: Double = 1.0

  override def rotation: Double = _rotation
  def rotation_=(degrees: Double): Unit = _rotation = degrees

  def draw(drawer: Drawer): Unit

  override lazy val gdx: ShapeDrawerDrawable = new ShapeDrawerDrawable() {
    private var alpha = 1.0

    override def draw(batch: Batch, x: Float, y: Float, width: Float, height: Float): Unit = {
      if (shapeDrawer == null || shapeDrawer.getBatch != batch) {
        setShapeDrawer(new ShapeDrawer(batch, Texture.Pixel.ref))
        drawer = Drawer(shapeDrawer)
      }
      alpha = batch.getColor.a.toDouble
      super.draw(batch, x, y, width, height)
    }

    override def drawShapes(shapeDrawer: ShapeDrawer, x: Float, y: Float, width: Float, height: Float): Unit = {
      drawer.shapeDrawer.update()
      drawer.reset(x.toDouble, y.toDouble, width.toDouble, height.toDouble, Color.White, rotation, alpha)

      self.draw(drawer)
    }
  }
}