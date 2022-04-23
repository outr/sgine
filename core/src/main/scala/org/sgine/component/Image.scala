package org.sgine.component

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import org.sgine.Color
import org.sgine.texture.Texture
import reactify._
import com.badlogic.gdx.scenes.scene2d.ui.{Image => GDXImage}
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Scaling

class Image extends ActorComponent[GDXImage] { component =>
  val texture: Var[Texture] = Var(Texture.Pixel)

  def this(path: String) = {
    this()
    texture @= Texture.internal(path)
  }

  def this(texture: Texture) = {
    this()
    this.texture @= texture
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

  texture.attachAndFire { texture =>
    actor.setDrawable(new TextureRegionDrawable(texture.ref))
    width := texture.scaledWidth * scaleX
    height := texture.scaledHeight * scaleY
  }
}