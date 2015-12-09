package org

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.utils.{Drawable, TextureRegionDrawable}
import org.sgine.transition.to.TransitionToPartial1
import pl.metastack.metarx.Sub

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

  implicit class Transitions(sub: Sub[Double]) {
    def transitionTo(to: Double)(implicit screen: Screen): TransitionToPartial1 = new TransitionToPartial1(screen, sub, to)
  }

  implicit class Times(i: Int) {
    def millis: Double = i.toDouble / 1000.0
    def seconds: Double = i.toDouble
    def minutes: Double = i.toDouble * 60.0
    def hours: Double = i.toDouble * 60.0 * 60.0
    def days: Double = i.toDouble * 60.0 * 60.0 * 24.0
  }
}