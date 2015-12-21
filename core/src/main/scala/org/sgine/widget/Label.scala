package org.sgine.widget

import com.badlogic.gdx.graphics.Colors
import com.badlogic.gdx.graphics.g2d.{Batch, BitmapFont}
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.{Label => GDXLabel}
import org.sgine._
import org.sgine.component.gdx.EnhancedActor
import org.sgine.component.prop.FontProperties
import org.sgine.component.{ActorWidget, DimensionedComponent}
import pl.metastack.metarx.{ReadChannel, Sub}

class Label(implicit val screen: Screen) extends ActorWidget[GDXLabel with EnhancedActor] {
  def this(text: String)(implicit screen: Screen) {
    this()(screen)
    this.text := text
  }
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

  override lazy val actor: GDXLabel with EnhancedActor = new GDXLabel("", new LabelStyle()) with EnhancedActor {
    override def component: DimensionedComponent = Label.this

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
  val font: FontProperties = new FontProperties
  val bitmapFont: Sub[Option[BitmapFont]] = Sub[Option[BitmapFont]](None)
  val wrap: Sub[Boolean] = Sub[Boolean](false)
  val ellipsis: Sub[Option[String]] = Sub[Option[String]](None)

  font.family := ui.theme.font.family
  font.style := ui.theme.font.style
  font.size := ui.theme.font.size
  ellipsis := ui.theme.ellipsis

  screen.render.once {
    text.attach { s =>
      actor.setText(s)
      updatePreferredSize()
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
        updatePreferredSize()
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

  override def updatePreferredSize(): Unit = if (actor.getStyle != null && actor.getStyle.font != null) {
    super.updatePreferredSize()
  }
}