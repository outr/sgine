package org.sgine.component

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import reactify._
import com.badlogic.gdx.scenes.scene2d.ui.{Image => GDXImage}
import com.badlogic.gdx.utils.Scaling
import org.sgine.drawable.{Drawable, Texture}

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
  }

  drawable.attachAndFire { drawable =>
    actor.setDrawable(drawable.gdx)
    width := drawable.scaledWidth * scaleX
    height := drawable.scaledHeight * scaleY
  }
}