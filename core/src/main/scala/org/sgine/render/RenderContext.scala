package org.sgine.render

import com.badlogic.gdx.graphics.g2d.{BitmapFont, GlyphLayout, NinePatch, SpriteBatch}
import com.badlogic.gdx.math.Matrix4
import org.sgine.texture.Texture
import org.sgine.{Color, Screen}
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
    font.setColor(color.gdx)
    font.draw(spriteBatch, layout, 0.0f, 0.0f)
  }

  def draw(texture: Texture, transform: Matrix4, color: Color): Unit = {
    spriteBatch.setTransformMatrix(transform)
    spriteBatch.setColor(color.gdx)
    spriteBatch.draw(texture.ref, 0.0f, 0.0f, texture.scaledWidth.toFloat, texture.scaledHeight.toFloat)
  }

  def draw(ninePatch: NinePatch, transform: Matrix4, width: Double, height: Double): Unit = {
    spriteBatch.setTransformMatrix(transform)
    ninePatch.draw(spriteBatch, 0.0f, 0.0f, width.toFloat, height.toFloat)
  }

  def filledRectangle(x: Double, y: Double, width: Double, height: Double, transform: Matrix4, color: Color): Unit = {
    spriteBatch.setTransformMatrix(transform)
    shapeDrawer.filledRectangle(x.toFloat, y.toFloat, width.toFloat, height.toFloat, color.gdx)
  }

  def rectangle(x: Double, y: Double, width: Double, height: Double, transform: Matrix4, color: Color, lineWidth: Double): Unit = {
    spriteBatch.setTransformMatrix(transform)
    shapeDrawer.rectangle(x.toFloat, y.toFloat, width.toFloat, height.toFloat, color.gdx, lineWidth.toFloat)
  }

  def circle(x: Double, y: Double, radius: Double, transform: Matrix4, color: Color, lineWidth: Double, joinType: JoinType): Unit = {
    spriteBatch.setTransformMatrix(transform)
    shapeDrawer.setColor(color.gdx)
    shapeDrawer.circle(x.toFloat, y.toFloat, radius.toFloat, lineWidth.toFloat, joinType)
  }

  def line(x1: Double, y1: Double, x2: Double, y2: Double, transform: Matrix4, color: Color, lineWidth: Double): Unit = {
    spriteBatch.setTransformMatrix(transform)
    shapeDrawer.line(x1.toFloat, y1.toFloat, x2.toFloat, y2.toFloat, color.gdx, lineWidth.toFloat)
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