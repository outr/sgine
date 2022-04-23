package org.sgine.component
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.{Batch, BitmapFont, GlyphLayout}
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import org.sgine.{Color, Screen}
import com.badlogic.gdx.scenes.scene2d.ui.{Label => GDXLabel}
import org.sgine.render.RenderContext
import reactify._

class Label extends ActorComponent[GDXLabel] { component =>
  val font: Var[BitmapFont] = Var(RenderContext.fontNormal)
  val text: Var[String] = Var("")
  object preferred {
    lazy val width: Var[Double] = Var(0.0)
    lazy val height: Var[Double] = Var(0.0)
  }

  def this(text: String) = {
    this()
    this.text @= text
  }

  font.on(validateDimensions @= true)
  text.on(validateDimensions @= true)
  width := preferred.width
  height := preferred.height

  override lazy val actor: GDXLabel = new GDXLabel(text(), new LabelStyle(font(), color().gdx)) {
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

  override protected def updateDimensions(screen: Screen): Unit = {
    actor.setStyle(new LabelStyle(font(), color().gdx))
    actor.setText(text())
    preferred.width @= actor.getPrefWidth.toDouble
    preferred.height @= actor.getPrefHeight.toDouble

    super.updateDimensions(screen)
  }
}
