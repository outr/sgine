package org.sgine.widget

import com.badlogic.gdx.scenes.scene2d.ui.{Image => GDXImage}
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.utils.Scaling
import org.sgine.Screen
import org.sgine.component.gdx.EnhancedActor
import org.sgine.component.{ActorWidget, DimensionedComponent}
import pl.metastack.metarx.Sub

class Image(implicit val screen: Screen) extends ActorWidget[GDXImage with EnhancedActor] {
  def this(drawable: => Drawable)(implicit screen: Screen) = {
    this()(screen)
    screen.render.once {
      this.drawable := Some(drawable)
      preferred._width := drawable.getMinWidth
      preferred._height := drawable.getMinHeight
    }
  }

  lazy val actor: GDXImage with EnhancedActor = new GDXImage with EnhancedActor {
    setScaling(Scaling.stretch)

    override def component: DimensionedComponent = Image.this
  }

  val drawable: Sub[Option[Drawable]] = Sub(None)

  drawable.attach(d => actor.setDrawable(d.orNull))
}

object Image {
  def apply(drawable: Drawable)(implicit screen: Screen): Image = new Image(drawable)(screen)
}