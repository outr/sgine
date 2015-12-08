package org

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.utils.{Drawable, TextureRegionDrawable}

import scala.language.implicitConversions

package object sgine {
  private var textureMap = Map.empty[String, Texture]
  private var textureRegionMap = Map.empty[String, TextureRegion]

  def ui = UI()

  implicit def string2Texture(classPath: String): Texture = synchronized {
    textureMap.get(classPath) match {
      case Some(texture) => texture
      case None => {
        val texture = new Texture(Gdx.files.classpath(classPath))
        textureMap += classPath -> texture
        texture
      }
    }
  }

  implicit def string2TextureRegion(classPath: String): TextureRegion = synchronized {
    val texture = string2Texture(classPath)
    textureRegionMap.get(classPath) match {
      case Some(region) => region
      case None => {
        val region = new TextureRegion(texture, texture.getWidth, texture.getHeight)
        textureRegionMap += classPath -> region
        region
      }
    }
  }

  implicit def string2Drawable(classPath: String): Drawable = {
    new TextureRegionDrawable(string2TextureRegion(classPath))
  }
}
