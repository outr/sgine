package org.sgine.render

import com.badlogic.gdx.graphics.g2d.{BitmapFont, GlyphLayout, NinePatch, SpriteBatch}
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Matrix4
import org.sgine.texture.Texture
import org.sgine.{Alignment, Screen}
import space.earlygrey.shapedrawer.{JoinType, ShapeDrawer}

class RenderContext(val screen: Screen) {
  import RenderContext._

  def renderWith[Return](f: => Return): Return = {
    spriteBatch.begin()
    try {
      spriteBatch.setProjectionMatrix(screen.camera.combined)
      shapeDrawer.update()
      f
    } finally {
      spriteBatch.end()
    }
  }

  def draw(font: BitmapFont, layout: GlyphLayout, transform: Matrix4, color: Color): Unit = {
    spriteBatch.setTransformMatrix(transform)
    font.setColor(color)
    font.draw(spriteBatch, layout, 0.0f, 0.0f)
  }

  def draw(texture: Texture, transform: Matrix4, color: Color): Unit = {
    spriteBatch.setTransformMatrix(transform)
    spriteBatch.setColor(color)
    spriteBatch.draw(texture.ref, 0.0f, 0.0f)
  }

  def draw(ninePatch: NinePatch, transform: Matrix4, width: Double, height: Double): Unit = {
    spriteBatch.setTransformMatrix(transform)
    ninePatch.draw(spriteBatch, 0.0f, 0.0f, width.toFloat, height.toFloat)
  }

  /*def draw(ninePatch: NinePatch, x: Double, y: Double, width: Double, height: Double): Unit = {
    val posX = x.toFloat
    val posY = translateY(y, height, 1.0)
    ninePatch.draw(spriteBatch, posX, posY, width.toFloat, height.toFloat)
  }*/

  def filledRectangle(width: Double, height: Double, transform: Matrix4, color: Color): Unit = {
//    val posX = x.toFloat
//    val posY = translateY(y, height, 1.0)
    spriteBatch.setTransformMatrix(transform)
    shapeDrawer.filledRectangle(0.0f, 0.0f, width.toFloat, height.toFloat, color)
  }

  def rectangle(width: Double, height: Double, transform: Matrix4, color: Color, lineWidth: Double): Unit = {
//    val posX = x.toFloat
//    val posY = translateY(y, height, 1.0)
    spriteBatch.setTransformMatrix(transform)
    shapeDrawer.rectangle(0.0f, 0.0f, width.toFloat, height.toFloat, color, lineWidth.toFloat)
  }

  def circle(radius: Double, transform: Matrix4, color: Color, lineWidth: Double, joinType: JoinType): Unit = {
//    val posX = x.toFloat
//    val posY = translateY(y, 0.0, 1.0)
    spriteBatch.setTransformMatrix(transform)
    shapeDrawer.setColor(color)
    shapeDrawer.circle(0.0f, 0.0f, radius.toFloat, lineWidth.toFloat, joinType)
  }

  def line(x: Double, y: Double, transform: Matrix4, color: Color, lineWidth: Double): Unit = {
//    val posX1 = x1.toFloat
//    val posY1 = translateY(y1, 0.0, 1.0)
//    val posX2 = x2.toFloat
//    val posY2 = translateY(y2, 0.0, 1.0)
    spriteBatch.setTransformMatrix(transform)
    shapeDrawer.line(0.0f, 0.0f, x.toFloat, y.toFloat, color, lineWidth.toFloat)
  }
}

object RenderContext {
  private lazy val spriteBatch: SpriteBatch = new SpriteBatch
  private lazy val shapeDrawer: ShapeDrawer = new ShapeDrawer(spriteBatch, Texture.Pixel.ref)
  lazy val fontNormal: BitmapFont = {
    val f = new BitmapFont
    f.getData.setScale(4.0f)
    f
  }
  lazy val fontSmall: BitmapFont = {
    val f = new BitmapFont
    f.getData.setScale(3.0f)
    f
  }

  def init(): Unit = {
    spriteBatch
    shapeDrawer
    fontNormal
  }

  def dispose(): Unit = {
    spriteBatch.dispose()
    fontNormal.dispose()
  }
}