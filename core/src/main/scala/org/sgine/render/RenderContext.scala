package org.sgine.render


import com.badlogic.gdx.graphics.Pixmap.Format
import com.badlogic.gdx.graphics.g2d.{BitmapFont, GlyphLayout, NinePatch, SpriteBatch, TextureRegion}
import com.badlogic.gdx.graphics.{Color, Pixmap, Texture}
import org.sgine.texture.TextureReference
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

  private def translateY(y: Double, height: Double, scaleY: Double): Float =
    (-y + screen.height() - (height * scaleY)).toFloat

  def draw(text: String, x: Double, y: Double, alignment: Alignment, color: Color, font: BitmapFont = fontNormal): Unit = {
    val layout = new GlyphLayout(font, text)
    val width = layout.width.toDouble
    val posX = (alignment match {
      case Alignment.Left => x
      case Alignment.Center => x - (width / 2.0)
      case Alignment.Right => x - width
    }).toFloat
    val posY = translateY(y, 0.0, 1.0)

    font.setColor(color)
    font.draw(spriteBatch, text, posX, posY)
  }

  def draw(ref: TextureReference,
           x: Double,
           y: Double,
           scaleX: Double,
           scaleY: Double,
           rotation: Double,
           color: Color,
           flipX: Boolean,
           flipY: Boolean): Unit = {
    val sX = ref.scaleX * scaleX
    val sY = ref.scaleY * scaleY
    val posX = x.toFloat
    val posY = translateY(y, ref.height, sY)
    val originX = if (sX == 1.0) (ref.width / 2.0).toFloat else 0.0f
    val originY = if (sY == 1.0) (ref.height / 2.0).toFloat else 0.0f
    val r = -(ref.rotation + rotation)
    spriteBatch.setColor(color)
    try {
      if (flipX != ref.ref.isFlipX) {
        ref.ref.flip(true, false)
      }
      if (flipY != ref.ref.isFlipY) {
        ref.ref.flip(false, true)
      }
      spriteBatch.draw(
        ref.ref,
        posX,
        posY,
        originX,
        originY,
        ref.width.toFloat,
        ref.height.toFloat,
        sX.toFloat,
        sY.toFloat,
        r.toFloat
      )
    } finally {
      spriteBatch.setColor(Color.WHITE)
    }
  }

  def draw(ninePatch: NinePatch, x: Double, y: Double, width: Double, height: Double): Unit = {
    val posX = x.toFloat
    val posY = translateY(y, height, 1.0)
    ninePatch.draw(spriteBatch, posX, posY, width.toFloat, height.toFloat)
  }

  def filledRectangle(x: Double, y: Double, width: Double, height: Double, color: Color): Unit = {
    val posX = x.toFloat
    val posY = translateY(y, height, 1.0)
    shapeDrawer.filledRectangle(posX, posY, width.toFloat, height.toFloat, color)
  }

  def rectangle(x: Double, y: Double, width: Double, height: Double, color: Color, lineWidth: Double): Unit = {
    val posX = x.toFloat
    val posY = translateY(y, height, 1.0)
    shapeDrawer.rectangle(posX, posY, width.toFloat, height.toFloat, color, lineWidth.toFloat)
  }

  def circle(x: Double, y: Double, radius: Double, color: Color, lineWidth: Double, joinType: JoinType): Unit = {
    val posX = x.toFloat
    val posY = translateY(y, 0.0, 1.0)
    shapeDrawer.setColor(color)
    shapeDrawer.circle(posX, posY, radius.toFloat, lineWidth.toFloat, joinType)
  }

  def line(x1: Double, y1: Double, x2: Double, y2: Double, color: Color, lineWidth: Double): Unit = {
    val posX1 = x1.toFloat
    val posY1 = translateY(y1, 0.0, 1.0)
    val posX2 = x2.toFloat
    val posY2 = translateY(y2, 0.0, 1.0)
    shapeDrawer.line(posX1, posY1, posX2, posY2, color, lineWidth.toFloat)
  }
}

object RenderContext {
  private lazy val spriteBatch: SpriteBatch = new SpriteBatch
  private lazy val shapeDrawer: ShapeDrawer = {
    val pixmap = new Pixmap(1, 1, Format.RGBA8888)
    pixmap.setColor(Color.WHITE)
    pixmap.drawPixel(0, 0)
    val texture = new Texture(pixmap)
    pixmap.dispose()
    val region = new TextureRegion(texture, 0, 0, 1, 1)
    new ShapeDrawer(spriteBatch, region)
  }
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