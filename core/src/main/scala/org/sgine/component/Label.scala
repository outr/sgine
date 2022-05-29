package org.sgine.component

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.{Batch, BitmapFont}
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.{Label => GDXLabel}
import org.sgine.{Screen, UI}
import reactify._

class Label extends ActorComponent[GDXLabel] { component =>
  val font: Var[BitmapFont] = Var(UI.fontNormal)
  val text: Var[String] = Var("")
  val wrap: Var[Boolean] = Var(true)

  def this(text: String) = {
    this()
    this.text @= text
  }

  override val actor: GDXLabel = new GDXLabel(text(), new LabelStyle(font(), color().gdx)) {
    setUserObject(component)

    override def draw(batch: Batch, parentAlpha: Float): Unit = {
      render @= Gdx.graphics.getDeltaTime.toDouble
      super.draw(batch, parentAlpha)
    }

    override def act(delta: Float): Unit = {
      update(delta.toDouble)
      super.act(delta)
    }
  }

  override protected def init(): Unit = {
    super.init()

    font.on(validateDimensions @= true)
    text.on(validateDimensions @= true)
    wrap.on(validateDimensions @= true)
  }

  override protected def updateDimensions(screen: Screen): Unit = {
    actor.setStyle(new LabelStyle(font(), color().gdx))
    actor.setText(text)
    actor.setWrap(wrap)
    preferred.width @= actor.getPrefWidth.toDouble
    preferred.height @= actor.getPrefHeight.toDouble

    super.updateDimensions(screen)
  }
}
