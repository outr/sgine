package org.sgine

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.sgine.task.TaskManager
import pl.metastack.metarx.{ReadStateChannel, Sub, Var}

/**
  * UI is the primary entry point into an Sgine application. See BasicUI and StandardUI for common use-case scenarios.
  */
trait UI extends RenderFlow with InputSupport {
  def screen: Screen

  UI.instance = Some(this)

  private[sgine] var textureMap = Map.empty[String, Texture]
  private[sgine] var textureRegionMap = Map.empty[String, TextureRegion]

  /**
    * The graphical theme for all elements within this UI
    */
  val theme: Theme = new Theme

  /**
    * The current delta between renders
    */
  def delta: Double = Gdx.graphics.getDeltaTime.toDouble
  private val _width = Sub(0.0)
  private val _height = Sub(0.0)

  /**
    * Display width
    */
  def width: ReadStateChannel[Double] = _width

  /**
    * Display height
    */
  def height: ReadStateChannel[Double] = _height

  /**
    * Center point of the UI (width / 2.0)
    */
  lazy val center: ReadStateChannel[Double] = Sub(_width / 2.0)

  /**
    * Middle point of the UI (height / 2.0)
    */
  lazy val middle: ReadStateChannel[Double] = Sub(_height / 2.0)

  /**
    * Display aspect ratio
    */
  lazy val aspectRatio: ReadStateChannel[Double] = {
    val s = Sub(0.0)
    s := width / height
    s
  }

  /**
    * True if continuousRendering should be enabled. If false, rendering will be handled internally or when
    * invalidateDisplay() is called. Defaults to true.
    */
  val continuousRendering: Sub[Boolean] = Sub(true)

  /**
    * True if the UI should exit on error. Defaults to true.
    */
  val exitOnError: Var[Boolean] = Var[Boolean](true)

  /**
    * Manages all asynchronous operations within the application
    */
  lazy val taskManager: TaskManager = new TaskManager()   // TODO: make configurable

  private[sgine] val listener = new GDXApplicationListener(this)

  create.once {
    continuousRendering.attach(cr => Gdx.graphics.setContinuousRendering(cr))
    taskManager.start()
  }
  resize.on {
    _width := Gdx.graphics.getWidth.toDouble
    _height := Gdx.graphics.getHeight.toDouble
    invalidateDisplay()
  }

  /**
    * Invalidates the display and request re-rendering. Only necessary if continuousRendering is set to false.
    */
  def invalidateDisplay(): Unit = Gdx.graphics.requestRendering()

  /**
    * Logs an info message
    */
  def info(message: String): Unit = {
    println(message)
  }

  /**
    * Logs a warning message
    */
  def warn(message: String): Unit = {
    println(s"WARN: $message")
  }

  /**
    * Logs an error
    */
  def error(t: Throwable, message: Option[String] = None) = {
    // TODO: support logging
    System.err.println(message.getOrElse("An Error Occurred"))
    t.printStackTrace()
    if (exitOnError.get) {
      Gdx.app.exit()
    }
  }

  /**
    * Convenience method to catch and report errors.
    */
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