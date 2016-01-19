package org.sgine.widget

import com.badlogic.gdx.scenes.scene2d.ui.{Image => GDXImage}
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.utils.Scaling
import org.sgine.Screen
import org.sgine.component.gdx.EnhancedActor
import org.sgine.component.{ActorWidget, DimensionedComponent}
import pl.metastack.metarx.Sub

class Image(implicit scrn: Screen) extends ActorWidget[GDXImage with EnhancedActor] {
  override def screen: Screen = scrn

  def this(drawable: => Drawable)(implicit screen: Screen) = {
    this()(screen)
    screen.render.once {
      this.drawable := Some(drawable)
    }
  }

  lazy val actor: GDXImage with EnhancedActor = new GDXImage with EnhancedActor {
    setScaling(Scaling.stretch)

    override def component: DimensionedComponent = Image.this
  }

  val drawable: Sub[Option[Drawable]] = Sub(None)

  drawable.attach(d => updatePreferred())

  private def updatePreferred(): Unit = {
    val d = drawable.get
    val (pw, ph) = d match {
      case Some(dr) => dr.getMinWidth.toDouble -> dr.getMinHeight.toDouble
      case None => 0.0 -> 0.0
    }
    preferred._width := pw
    preferred._height := ph
    actor.setDrawable(d.orNull)
  }
}

object Image {
  def apply(drawable: Drawable)(implicit screen: Screen): Image = new Image(drawable)(screen)
}