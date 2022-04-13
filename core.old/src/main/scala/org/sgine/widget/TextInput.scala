package org.sgine.widget

import com.badlogic.gdx.graphics.Colors
import com.badlogic.gdx.graphics.g2d.{Batch, BitmapFont}
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle
import com.badlogic.gdx.scenes.scene2d.utils.{ChangeListener, Drawable, FocusListener}
import com.badlogic.gdx.scenes.scene2d.{Event, EventListener}
import io.youi.Color
import org.sgine._
import org.sgine.component.gdx.EnhancedActor
import org.sgine.component.prop.FontProperties
import org.sgine.component.{ActorWidget, DimensionedComponent, Focusable}
import reactify._

class TextInput(implicit val screen: Screen) extends ActorWidget[TextField] with Focusable {
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
  def this(text: String, family: String, style: String, size: Val[Int])(implicit screen: Screen) {
    this()(screen)
    this.text := text
    font.family := family
    font.style := style
    font.size := size
  }

  private var updateDelay = 0.25

  override lazy val actor: GDXTextField = new GDXTextField(this)

  val text: Var[String] = Var[String]("")
  val placeholder: Var[String] = Var[String]("")
  val font: FontProperties = new FontProperties
  val bitmapFont: Var[Option[BitmapFont]] = Var[Option[BitmapFont]](None)
  val maskCharacter: Var[Option[Char]] = Var[Option[Char]](None)
  val blinkTime: Var[Double] = Var[Double](0.32)
  val disabled: Var[Boolean] = Var[Boolean](false)
  val selectionColor: Var[Color] = Var[Color](Color.LightCoral)
  val placeholderColor: Var[Color] = Var[Color](Color.DimGray)

  font.family := ui.theme.font.family
  font.style := ui.theme.font.style
  font.size := ui.theme.font.size
  blinkTime := ui.theme.blinkTime
  selectionColor := ui.theme.selectionColor
  placeholderColor := ui.theme.placeholderColor

  def copy(): Unit = actor.copy()
  def cut(): Unit = actor.cut()
  def selection(): String = actor.getSelection
  def selection(start: Int, end: Int): Unit = actor.setSelection(start, end)
  def selectAll(): Unit = actor.selectAll()
  def clearSelection(): Unit = actor.clearSelection()

  screen.render.once {
    text.attach { s =>
      if (s != actor.getText) {
        actor.setText(s)
        updatePreferredSize()
      }
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
    placeholderColor.attach { c =>
      val style = actor.getStyle
      if (style != null) {
        style.messageFontColor = c
      }
    }
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
        updatePreferredSize()
      }
    }
  }

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
    val style = new TextFieldStyle(bf, Colors.get("WHITE"), cursor, selection, background)
    style.messageFontColor = placeholderColor.get
    style
  }

  override def updatePreferredSize(): Unit = if (actor.getStyle != null && actor.getStyle.font != null) {
    super.updatePreferredSize()
  }
}

class GDXTextField(textInput: TextInput) extends TextField("", new TextFieldStyle()) with EnhancedActor {
  addListener(new EventListener {
    override def handle(event: Event): Boolean = {
      event match {
        case evt: ChangeListener.ChangeEvent => textInput.text := getText
        case evt: FocusListener.FocusEvent if evt.isFocused => textInput.requestFocus()
        case _ => // Ignore others
      }
      false
    }
  })

  override def component: DimensionedComponent = textInput

  override def setStyle(style: TextFieldStyle): Unit = {
    if (style != null && style.font != null) {
      super.setStyle(style)
    }
  }

  private var first = true
  override def draw(batch: Batch, parentAlpha: Float): Unit = {
    if (getStyle != null && getStyle.font != null) {
      if (first) {
        setText(textInput.text.get)
        if (textInput.maskCharacter.get.isDefined) setPasswordCharacter(textInput.maskCharacter.get.get)
        setPasswordMode(textInput.maskCharacter.get.isDefined)
        invalidate()
        textInput.updatePreferredSize()
        first = false
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

  override def getPrefWidth: Float = if (getStyle != null && getStyle.font != null) {
    layout.width + 3.0f
  } else {
    150.0f
  }

  override def drawSelection(selection: Drawable, batch: Batch, font: BitmapFont, x: Float, y: Float): Unit = {
    val c = textInput.selectionColor.get
    batch.setColor(c)
    super.drawSelection(selection, batch, font, x, y)
  }
}