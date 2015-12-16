package org.sgine.widget

import com.badlogic.gdx.graphics.Colors
import com.badlogic.gdx.graphics.g2d.{Batch, BitmapFont}
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.{Label => GDXLabel}
import org.sgine._
import org.sgine.component.ActorWidget
import pl.metastack.metarx.{ReadChannel, ReadStateChannel, Sub, Var}

class Label private(implicit val screen: Screen) extends ActorWidget[GDXLabel] {
  def this(text: String, family: String, style: String, size: Int)(implicit screen: Screen) {
    this()(screen)
    this.text := text
    font.family := family
    font.style := style
    font.size := size
  }
  def this(text: String, family: String, style: String, size: ReadChannel[Int])(implicit screen: Screen) {
    this()(screen)
    this.text := text
    font.family := family
    font.style := style
    font.size := size
  }

  override lazy val actor: GDXLabel = new GDXLabel("", new LabelStyle()) {
    override def setStyle(style: LabelStyle): Unit = {
      if (style != null && style.font != null) {
        super.setStyle(style)
      }
    }

    override def draw(batch: Batch, parentAlpha: Float): Unit = {
      if (getStyle != null && getStyle.font != null) {
        super.draw(batch, parentAlpha)
      }
    }
  }

  val text: Sub[String] = Sub[String]("")
  val font: Font = new Font
  val bitmapFont: Sub[Option[BitmapFont]] = Sub[Option[BitmapFont]](None)
  val wrap: Sub[Boolean] = Sub[Boolean](false)
  val ellipsis: Sub[Option[String]] = Sub[Option[String]](None)

  object preferred {
    private[widget] val _width = Var[Double](0.0)
    private[widget] val _height = Var[Double](0.0)

    def width: ReadStateChannel[Double] = _width
    def height: ReadStateChannel[Double] = _height
  }

  size.width := preferred._width
  size.height := preferred._height
  screen.render.once {
    text.attach { s =>
      actor.setText(s)
      updateSize()
    }
    font.family.attach(s => delayedUpdate())
    font.style.attach(s => delayedUpdate())
    font.size.attach(i => delayedUpdate())
    wrap.attach(b => actor.setWrap(b))
    ellipsis.attach(e => actor.setEllipsis(e.orNull))
    screen.render.on {
      if (updateDelay != -1.0) {
        updateDelay -= ui.delta
        if (updateDelay <= 0.0) {
          updateDelay = -1.0
          updateBitmapFont()
        }
      }
    }
  }
  bitmapFont.attach { bfOption =>
    if (bfOption.isDefined) {
      screen.render.once {
        actor.setStyle(labelStyle())
        updateSize()
      }
    }
  }

  private var updateDelay = 0.25

  private def delayedUpdate(): Unit = {
    updateDelay = 0.25
  }

  private def updateBitmapFont(): Unit = if (font.family.get.nonEmpty && font.size.get > 0 && font.style.get.nonEmpty) {
    // Load and apply the font
    ui.taskManager.font(font.family.get, font.style.get, font.size.get).andThen { bf =>
      bitmapFont := Option(bf)
    }
  }

  private def labelStyle(): LabelStyle = {
    val bf = bitmapFont.get.getOrElse(throw new NullPointerException("BitmapFont cannot be empty."))
    new LabelStyle(bf, Colors.get("WHITE"))
  }

  private def updateSize(): Unit = if (actor.getStyle != null && actor.getStyle.font != null) {
    if (actor.getPrefWidth != 0.0) preferred._width := actor.getPrefWidth
    if (actor.getPrefHeight != 0.0) preferred._height := actor.getPrefHeight
  }
}

class Font {
  val family: Sub[String] = Sub[String]("")
  val style: Sub[String] = Sub[String]("")
  val size: Sub[Int] = Sub[Int](0)
}