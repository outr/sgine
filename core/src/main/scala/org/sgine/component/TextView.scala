package org.sgine.component
import com.badlogic.gdx.graphics.g2d.{BitmapFont, GlyphLayout}
import org.sgine.Color
import org.sgine.render.RenderContext
import reactify._

class TextView extends RenderableComponent {
  private val _dirty: Var[Boolean] = Var(true)
  private val _layout: Var[Option[GlyphLayout]] = Var(None)

  val font: Var[BitmapFont] = Var(RenderContext.fontNormal)
  val text: Var[String] = Var("")
  val color: Var[Color] = Var(Color.White)

  val dirty: Val[Boolean] = _dirty
  val layout: Val[Option[GlyphLayout]] = _layout

  font.on(_dirty @= true)
  text.on(_dirty @= true)

  private def updateLayout(): Option[GlyphLayout] = if (dirty) {
    _dirty @= false

    if (text().nonEmpty) {
      val layout = new GlyphLayout(font, text)
      width @= layout.width
      height @= layout.height
      _layout @= Some(layout)
    } else {
      width @= 0.0
      height @= 0.0
      _layout @= None
    }

    _layout
  } else {
    _layout
  }

  override def render(context: RenderContext): Unit = updateLayout().foreach { layout =>
    context.draw(
      font = font,
      layout = layout,
      transform = matrix4(context),
      color = color
    )
  }
}
