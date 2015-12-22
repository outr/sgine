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
        val useMipMaps = true
        val texture = new Texture(file, useMipMaps)
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

  implicit def texture2Drawable(texture: Texture): Drawable = {
    textureRegion2Drawable(new TextureRegion(texture))
  }

  def delay(time: Double): Delay = new Delay(time)

  def repeat(times: Int, transition: Transition): Repeat = new Repeat(times, transition)

  def forever(transition: Transition): Repeat = new Repeat(Int.MaxValue, transition)

  def function(f: => Unit): ActionTransition = new ActionTransition(() => f)

  /**
    * Waits for <code>condition</code> to return true. This method will wait
    * <code>time</code> (in seconds) for the condition and will return false
    * if the condition is not met within that time. Further, a negative value
    * for <code>time</code> will cause the wait to occur until the condition
    * is true.
    *
    * @param time
   *              The time to wait for the condition to return true.
    * @param precision
   *              The recycle period between checks. Defaults to 0.01s.
    * @param start
   *              The start time in milliseconds since epoc. Defaults to
    *              System.currentTimeMillis.
    * @param errorOnTimeout
   *              If true, throws a java.util.concurrent.TimeoutException upon
    *              timeout. Defaults to false.
    * @param condition
   *              The functional condition that must return true.
    */
  @scala.annotation.tailrec
  def waitFor(time: Double, precision: Double = 0.01, start: Long = System.currentTimeMillis, errorOnTimeout: Boolean = false)(condition: => Boolean): Boolean = {
    val p = precision.toMillis
    if (!condition) {
      if ((time >= 0.0) && (System.currentTimeMillis - start > time.toMillis)) {
        if (errorOnTimeout) throw new java.util.concurrent.TimeoutException()
        false
      } else {
        Thread.sleep(p)

        waitFor(time, precision, start, errorOnTimeout)(condition)
      }
    } else {
      true
    }
  }

  implicit def color2GDXColor(color: Color): graphics.Color = {
    new graphics.Color(color.red.toFloat, color.green.toFloat, color.blue.toFloat, color.alpha.toFloat)
  }

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
    def toMillis: Long = math.round(d * 1000.0)
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