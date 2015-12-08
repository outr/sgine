package org.sgine.widget

import com.badlogic.gdx.scenes.scene2d.ui.{Image => GDXImage}
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import org.sgine.Screen
import org.sgine.component.ActorWidget
import pl.metastack.metarx.Sub

class Image(implicit val screen: Screen) extends ActorWidget[GDXImage] {
  lazy val actor = new GDXImage

  val drawable: Sub[Option[Drawable]] = Sub(None)

  drawable.attach(d => actor.setDrawable(d.orNull))
}

object Image {
  def apply(drawable: Drawable)(implicit screen: Screen): Image = {
    val image = new Image
    image.drawable := Some(drawable)
    image.size.width := drawable.getMinWidth
    image.size.height := drawable.getMinHeight
    image
  }
}