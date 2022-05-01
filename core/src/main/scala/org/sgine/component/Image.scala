package org.sgine.component

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.ui.{Image => GDXImage}
import com.badlogic.gdx.utils.Scaling
import org.sgine.drawable.{Drawable, ShapeDrawable, Texture}
import reactify._

class Image extends ActorComponent[GDXImage] { component =>
  val drawable: Var[Drawable] = Var(Texture.Pixel)

  def this(path: String) = {
    this()
    drawable @= Texture.internal(path)
  }

  def this(drawable: Drawable) = {
    this()
    this.drawable @= drawable
  }

  override lazy val actor: GDXImage = new GDXImage {
    setScaling(Scaling.stretch)
    setUserObject(component)

    override def draw(batch: Batch, parentAlpha: Float): Unit = {
      render @= Gdx.graphics.getDeltaTime.toDouble
      super.draw(batch, parentAlpha)
    }

    override def act(delta: Float): Unit = {
      update(delta.toDouble)
      super.act(delta)
    }

    override def setRotation(degrees: Float): Unit = {
      super.setRotation(degrees)
      drawable() match {
        case sd: ShapeDrawable => sd.rotation = degrees
        case _ => // Ignore others
      }
    }

    override def toString: String = component.toString
  }

  drawable.attachAndFire { drawable =>
    actor.setDrawable(drawable.gdx)
    width := (drawable.width * drawable.scaleX) * scaleX
    height := (drawable.height * drawable.scaleY) * scaleY
  }
}