package org.sgine.widget

import com.badlogic.gdx.graphics.Colors
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.{Label => GDXLabel}
import org.sgine._
import org.sgine.component.ActorWidget
import pl.metastack.metarx.{ReadChannel, Sub}

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

  override lazy val actor: GDXLabel = new GDXLabel("", new LabelStyle())

  val text: Sub[String] = Sub[String]("")
  val font: Font = new Font
  val bitmapFont: Sub[Option[BitmapFont]] = Sub[Option[BitmapFont]](None)

  screen.render.once {
    text.attach(s => actor.setText(s))
    font.family.attach(s => updateBitmapFont())
    font.style.attach(s => updateBitmapFont())
    font.size.attach(i => updateBitmapFont())
  }
  bitmapFont.attach { bfOption =>
    screen.render.once {
      val bf = bfOption.getOrElse(throw new NullPointerException("BitmapFont cannot be empty."))
      actor.setStyle(new LabelStyle(bf, Colors.get("WHITE")))
    }
  }

  private def updateBitmapFont(): Unit = if (font.family.get.nonEmpty && font.size.get > 0 && font.style.get.nonEmpty) {
    // Load and apply the font
    ui.taskManager.font(font.family.get, font.style.get, font.size.get).andThen { bf =>
      bitmapFont := Option(bf)
    }
  }
}

class Font {
  val family: Sub[String] = Sub[String]("")
  val style: Sub[String] = Sub[String]("")
  val size: Sub[Int] = Sub[Int](0)
}