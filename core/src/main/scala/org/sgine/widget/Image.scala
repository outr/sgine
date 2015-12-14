package org.sgine.widget

import com.badlogic.gdx.scenes.scene2d.ui.{Image => GDXImage}
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import org.sgine.Screen
import org.sgine.component.ActorWidget
import pl.metastack.metarx.Sub

class Image(implicit val screen: Screen) extends ActorWidget[GDXImage] {
  def this(drawable: => Drawable)(implicit screen: Screen) = {
    this()(screen)
    screen.render.once {
      this.drawable := Some(drawable)
      size.width := drawable.getMinWidth
      size.height := drawable.getMinHeight
    }
  }

  lazy val actor = new GDXImage

  val drawable: Sub[Option[Drawable]] = Sub(None)

  drawable.attach(d => actor.setDrawable(d.orNull))
}

object Image {
  def apply(drawable: Drawable)(implicit screen: Screen): Image = new Image(drawable)(screen)
}