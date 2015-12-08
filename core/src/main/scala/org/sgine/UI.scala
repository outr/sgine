package org.sgine

import com.badlogic.gdx.Gdx
import pl.metastack.metarx.Sub

trait UI extends RenderFlow {
  UI.instance = Some(this)

  def delta = Gdx.graphics.getDeltaTime
  val width: Sub[Double] = Sub(0.0)
  val height: Sub[Double] = Sub(0.0)
  val continuousRendering: Sub[Boolean] = Sub(true)

  private[sgine] val listener = new GDXApplicationListener(this)

  def invalidateDisplay(): Unit = Gdx.graphics.requestRendering()

  def error(t: Throwable, message: Option[String] = None) = {
    // TODO: support logging
    System.err.println(message.getOrElse("An Error Occurred"))
    t.printStackTrace()
  }

  def catchErrors[R](f: => R) = try {
    f
  } catch {
    case t: Throwable => error(t)
  }
}

object UI {
  private var instance: Option[UI] = None

  def apply(): UI = instance.get

  def get(): Option[UI] = instance
}