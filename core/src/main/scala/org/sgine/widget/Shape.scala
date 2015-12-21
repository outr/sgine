package org.sgine.widget

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.scenes.scene2d.ui.{Widget => GDXWidget}
import org.sgine.component.gdx.EnhancedActor
import org.sgine.component.{ActorWidget, DimensionedComponent}
import org.sgine.{Color, Screen}

import scala.language.implicitConversions

abstract class Shape(implicit val screen: Screen) extends ActorWidget[ShapeActor with EnhancedActor] {
  lazy val actor: ShapeActor with EnhancedActor = new ShapeActor(this) with EnhancedActor {
    override def component: DimensionedComponent = Shape.this
  }
  private def sr: ShapeRenderer = actor.renderer

  def draw(): Unit

  lazy val c1 = new com.badlogic.gdx.graphics.Color()
  lazy val c2 = new com.badlogic.gdx.graphics.Color()
  lazy val c3 = new com.badlogic.gdx.graphics.Color()
  lazy val c4 = new com.badlogic.gdx.graphics.Color()

  private def resetColors(): Unit = {
    val c = sr.getColor
    c1.set(c.r, c.g, c.b, actor.alpha)
    c2.set(c.r, c.g, c.b, actor.alpha)
    c3.set(c.r, c.g, c.b, actor.alpha)
    c4.set(c.r, c.g, c.b, actor.alpha)
  }

  def updateColorAlphas(): Unit = {
    c1.a *= actor.alpha
    c2.a *= actor.alpha
    c3.a *= actor.alpha
    c4.a *= actor.alpha
  }

  protected def beginFilled(): Unit = sr.begin(ShapeType.Filled)
  protected def beginLine(): Unit = sr.begin(ShapeType.Line)
  protected def beginPoint(): Unit = sr.begin(ShapeType.Point)

  protected def end(): Unit = sr.end()

  protected def doFilled(f: => Unit): Unit = {
    beginFilled()
    try {
      f
    } finally {
      end()
    }
  }
  protected def doLine(f: => Unit): Unit = {
    beginLine()
    try {
      f
    } finally {
      end()
    }
  }
  protected def doPoint(f: => Unit): Unit = {
    beginPoint()
    try {
      f
    } finally {
      end()
    }
  }

  protected def setColor(color: Color): Unit = {
    sr.setColor(color.red.toFloat, color.green.toFloat, color.blue.toFloat, actor.alpha * color.alpha.toFloat)
    resetColors()
  }
  protected def setColor(red: Double, green: Double, blue: Double, alpha: Double): Unit = {
    sr.setColor(red.toFloat, green.toFloat, blue.toFloat, actor.alpha * alpha.toFloat)
    resetColors()
  }
  protected def flush(): Unit = sr.flush()
  protected def reset(): Unit = actor.reset()
  protected def identity(): Unit = sr.identity()

  protected def arc(x: Double, y: Double, radius: Double, start: Double, degrees: Double, segments: Int): Unit = {
    sr.arc(x.toFloat, y.toFloat, radius.toFloat, start.toFloat, degrees.toFloat, segments)
  }
  protected def circle(x: Double, y: Double, radius: Double, segments: Int): Unit = {
    sr.circle(x.toFloat, y.toFloat, radius.toFloat, segments)
  }
  protected def curve(x1: Double, y1: Double, cx1: Double, cy1: Double, cx2: Double, cy2: Double, x2: Double, y2: Double, segments: Int): Unit = {
    sr.curve(x1.toFloat, y1.toFloat, cx1.toFloat, cy1.toFloat, cx2.toFloat, cy2.toFloat, x2.toFloat, y2.toFloat, segments)
  }
  protected def ellipse(x: Double, y: Double, width: Double, height: Double, segments: Int): Unit = {
    sr.ellipse(x.toFloat, y.toFloat, width.toFloat, height.toFloat, segments)
  }
  protected def line(x1: Double, y1: Double, x2: Double, y2: Double): Unit = {
    sr.line(x1.toFloat, y1.toFloat, x2.toFloat, y2.toFloat, c1, c2)
    resetColors()
  }
  protected def line(x1: Double, y1: Double, x2: Double, y2: Double, c1: Color, c2: Color): Unit = {
    this.c1.set(c1.red.toFloat, c1.green.toFloat, c1.blue.toFloat, actor.alpha * c1.alpha.toFloat)
    this.c2.set(c2.red.toFloat, c2.green.toFloat, c2.blue.toFloat, actor.alpha * c2.alpha.toFloat)
    sr.line(x1.toFloat, y1.toFloat, x2.toFloat, y2.toFloat, this.c1, this.c2)
    resetColors()
  }
  protected def polygon(vertices: Double*): Unit = {
    sr.polygon(vertices.map(_.toFloat).toArray)
  }
  protected def polyline(vertices: Double*): Unit = {
    sr.polyline(vertices.map(_.toFloat).toArray)
  }
  protected def rect(x: Double, y: Double, width: Double, height: Double): Unit = {
    sr.rect(x.toFloat, y.toFloat, width.toFloat, height.toFloat, c1, c2, c3, c4)
    resetColors()
  }
  protected def rect(x: Double, y: Double, width: Double, height: Double, c1: Color, c2: Color, c3: Color, c4: Color): Unit = {
    this.c1.set(c1.red.toFloat, c1.green.toFloat, c1.blue.toFloat, actor.alpha * c1.alpha.toFloat)
    this.c2.set(c2.red.toFloat, c2.green.toFloat, c2.blue.toFloat, actor.alpha * c2.alpha.toFloat)
    this.c3.set(c3.red.toFloat, c3.green.toFloat, c3.blue.toFloat, actor.alpha * c3.alpha.toFloat)
    this.c4.set(c4.red.toFloat, c4.green.toFloat, c4.blue.toFloat, actor.alpha * c4.alpha.toFloat)
    sr.rect(x.toFloat, y.toFloat, width.toFloat, height.toFloat, this.c1, this.c2, this.c3, this.c4)
    resetColors()
  }
  protected def rotate(degrees: Double): Unit = {
    sr.rotate(0.0f, 0.0f, 1.0f, degrees.toFloat)
  }
  protected def scale(x: Double = 1.0, y: Double = 1.0): Unit = {
    sr.scale(x.toFloat, y.toFloat, 1.0f)
  }
  protected def translate(x: Double = 0.0, y: Double = 0.0): Unit = {
    sr.translate(x.toFloat, y.toFloat, 0.0f)
  }
  protected def triangle(x1: Double, y1: Double, x2: Double, y2: Double, x3: Double, y3: Double): Unit = {
    sr.triangle(x1.toFloat, y1.toFloat, x2.toFloat, y2.toFloat, x3.toFloat, y3.toFloat, c1, c2, c3)
    resetColors()
  }
  protected def triangle(x1: Double, y1: Double, x2: Double, y2: Double, x3: Double, y3: Double, c1: Color, c2: Color, c3: Color): Unit = {
    this.c1.set(c1.red.toFloat, c1.green.toFloat, c1.blue.toFloat, actor.alpha * c1.alpha.toFloat)
    this.c2.set(c2.red.toFloat, c2.green.toFloat, c2.blue.toFloat, actor.alpha * c2.alpha.toFloat)
    this.c3.set(c3.red.toFloat, c3.green.toFloat, c3.blue.toFloat, actor.alpha * c3.alpha.toFloat)
    sr.triangle(x1.toFloat, y1.toFloat, x2.toFloat, y2.toFloat, x3.toFloat, y3.toFloat, this.c1, this.c2, this.c3)
    resetColors()
  }
}

class ShapeActor(shape: Shape) extends GDXWidget {
  lazy val renderer = new ShapeRenderer
  private var batch: Batch = _
  var alpha: Float = _

  override def draw(batch: Batch, parentAlpha: Float): Unit = {
    super.draw(batch, parentAlpha)
    val c = getColor
    this.batch = batch
    this.alpha = parentAlpha * c.a

    batch.end()

    Gdx.gl.glEnable(GL20.GL_BLEND)
    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
    reset()
    shape.draw()
    Gdx.gl.glDisable(GL20.GL_BLEND)

    batch.begin()

    this.batch = null
  }

  def reset(): Unit = {
    renderer.setProjectionMatrix(batch.getProjectionMatrix)
    renderer.setTransformMatrix(batch.getTransformMatrix)
    renderer.translate(getX, getY, 0.0f)
    if (getRotation != 0.0f) {
      renderer.translate(getOriginX, getOriginY, 0.0f)
      renderer.rotate(0.0f, 0.0f, 1.0f, getRotation)
      renderer.translate(-getOriginX, -getOriginY, 0.0f)
    }
    val c = getColor
    renderer.setColor(c.r, c.g, c.b, alpha)
  }
}