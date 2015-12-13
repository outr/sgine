package org

import com.badlogic.gdx.graphics.Pixmap.Format
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.{Pixmap, Texture}
import com.badlogic.gdx.scenes.scene2d.utils.{Drawable, TextureRegionDrawable}
import com.badlogic.gdx.{Gdx, graphics}
import org.sgine.component.prop.DependentVar
import org.sgine.transition._
import pl.metastack.metarx.{ReadChannel, Sub}

import scala.language.implicitConversions

package object sgine {
  def ui = UI()

  def pixel: TextureRegion = getOrCreateTextureRegion("pixel", new TextureRegion(new Texture(createPixelMap(Color.White, 1, 1))))

  def createPixelMap(color: Color, width: Int, height: Int) = {
    val pixMap = new Pixmap(width, height, Format.RGBA8888)
    pixMap.setColor(new graphics.Color(color.red.toFloat, color.green.toFloat, color.blue.toFloat, color.alpha.toFloat))
    pixMap.fillRectangle(0, 0, width, height)
    pixMap
  }

  def getOrCreateTextureRegion(name: String, creator: => TextureRegion): TextureRegion = synchronized {
    ui.textureRegionMap.get(name) match {
      case Some(region) => region
      case None => {
        val region: TextureRegion = creator
        ui.textureRegionMap += name -> region
        region
      }
    }
  }

  implicit def string2Texture(classPath: String): Texture = synchronized {
    ui.textureMap.get(classPath) match {
      case Some(texture) => texture
      case None => {
        val file = Gdx.files.classpath(classPath)
        if (file == null) throw new NullPointerException(s"Unable to find $classPath in classpath.")
        val texture = new Texture(file)
        ui.textureMap += classPath -> texture
        texture
      }
    }
  }

  implicit def string2TextureRegion(classPath: String): TextureRegion = synchronized {
    val texture = string2Texture(classPath)
    ui.textureRegionMap.get(classPath) match {
      case Some(region) => region
      case None => {
        val region = new TextureRegion(texture, texture.getWidth, texture.getHeight)
        ui.textureRegionMap += classPath -> region
        region
      }
    }
  }

  implicit def string2Drawable(classPath: String): Drawable = {
    new TextureRegionDrawable(string2TextureRegion(classPath))
  }

  implicit def textureRegion2Drawable(region: TextureRegion): Drawable = {
    new TextureRegionDrawable(region)
  }

  def delay(time: Double): Delay = new Delay(time)

  def repeat(times: Int, transition: Transition): Repeat = new Repeat(times, transition)

  def forever(transition: Transition): Repeat = new Repeat(Int.MaxValue, transition)

  def function(f: => Unit): ActionTransition = new ActionTransition(() => f)

  implicit class Transitions(sub: Sub[Double]) {
    def transitionTo(to: => Double): TransitionTo = {
      new TransitionTo((d: Double) => sub := d, () => sub.get, () => to)
    }
  }

  implicit class DependentTransitions(dep: DependentVar) {
    def transitionTo(to: => Double): TransitionTo = {
      new TransitionTo((d: Double) => dep := d, () => dep.get, () => to)
    }
  }

  implicit class IntTimes(i: Int) {
    def millis: Double = i.toDouble / 1000.0
    def milliseconds: Double = i.toDouble / 1000.0
    def seconds: Double = i.toDouble
    def minutes: Double = i.toDouble * 60.0
    def hours: Double = i.toDouble * 60.0 * 60.0
    def days: Double = i.toDouble * 60.0 * 60.0 * 24.0
  }

  implicit class DoubleTimes(d: Double) {
    def millis: Double = d / 1000.0
    def milliseconds: Double = d / 1000.0
    def seconds: Double = d
    def minutes: Double = d * 60.0
    def hours: Double = d * 60.0 * 60.0
    def days: Double = d * 60.0 * 60.0 * 24.0
  }

  implicit class IntSize(i: Int) {
    def px: Double = i.toDouble
    def pctw: ReadChannel[Double] = (i.toDouble / 100.0) * ui.width
    def pcth: ReadChannel[Double] = (i.toDouble / 100.0) * ui.height
  }
  implicit class DoubleSize(d: Double) {
    def px: Double = d
    def pctw: ReadChannel[Double] = (d / 100.0) * ui.width
    def pcth: ReadChannel[Double] = (d / 100.0) * ui.height
  }
}