package org.sgine.widget

import com.badlogic.gdx.graphics.Colors
import com.badlogic.gdx.graphics.g2d.{Batch, BitmapFont}
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import org.sgine._
import org.sgine.component.ActorWidget
import org.sgine.component.prop.{FontProperties, PreferredSize}
import pl.metastack.metarx.{ReadChannel, Sub}

class TextInput private(implicit val screen: Screen) extends ActorWidget[TextField] {
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

  override lazy val actor: TextField = new TextField("", new TextFieldStyle()) {
    override def setStyle(style: TextFieldStyle): Unit = {
      if (style != null && style.font != null) {
        super.setStyle(style)
      }
    }

    override def draw(batch: Batch, parentAlpha: Float): Unit = {
      if (getStyle != null && getStyle.font != null) {
        if (getText != TextInput.this.text.get) {
          setText(TextInput.this.text.get)
          if (maskCharacter.get.isDefined) setPasswordCharacter(maskCharacter.get.get)
          setPasswordMode(maskCharacter.get.isDefined)
          invalidate()
        }
        super.draw(batch, parentAlpha)
      }
    }

    override def setText(str: String): Unit = if (getStyle != null && getStyle.font != null) {
      super.setText(str)
    }

    override def setPasswordMode(passwordMode: Boolean): Unit = if (getStyle != null && getStyle.font != null) {
      super.setPasswordMode(passwordMode)
    }

    override def getPrefHeight: Float = if (getStyle != null && getStyle.font != null) {
      super.getPrefHeight
    } else {
      0.0f
    }
  }

  val text: Sub[String] = Sub[String]("")
  val placeholder: Sub[String] = Sub[String]("")
  val font: FontProperties = new FontProperties
  val bitmapFont: Sub[Option[BitmapFont]] = Sub[Option[BitmapFont]](None)
  val preferred: PreferredSize = new PreferredSize
  val maskCharacter: Sub[Option[Char]] = Sub[Option[Char]](None)
  val blinkTime: Sub[Double] = Sub[Double](0.32)
  val disabled: Sub[Boolean] = Sub[Boolean](false)

  def copy(): Unit = actor.copy()
  def cut(): Unit = actor.cut()
  def selection(): String = actor.getSelection
  def selection(start: Int, end: Int): Unit = actor.setSelection(start, end)
  def selectAll(): Unit = actor.selectAll()
  def clearSelection(): Unit = actor.clearSelection()

  size.width := preferred._width
  size.height := preferred._height
  screen.render.once {
    text.attach { s =>
      actor.setText(s)
      updateSize()
    }
    placeholder.attach(s => actor.setMessageText(s))
    maskCharacter.attach {
      case Some(c) => {
        actor.setPasswordCharacter(c)
        actor.setPasswordMode(true)
      }
      case None => actor.setPasswordMode(false)
    }
    blinkTime.attach(d => actor.setBlinkTime(d.toFloat))
    disabled.attach(b => actor.setDisabled(b))
    font.family.attach(s => delayedUpdate())
    font.style.attach(s => delayedUpdate())
    font.size.attach(i => delayedUpdate())
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
        actor.setStyle(textFieldStyle())
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

  private def textFieldStyle(): TextFieldStyle = {
    val bf = bitmapFont.get.getOrElse(throw new NullPointerException("BitmapFont cannot be empty."))
    val cursor: Drawable = pixel
    val selection: Drawable = pixel
    val background: Drawable = null
    new TextFieldStyle(bf, Colors.get("WHITE"), cursor, selection, background)
  }

  private def updateSize(): Unit = if (actor.getStyle != null && actor.getStyle.font != null) {
    if (actor.getPrefWidth != 0.0) preferred._width := actor.getPrefWidth
    if (actor.getPrefHeight != 0.0) preferred._height := actor.getPrefHeight
  }
}