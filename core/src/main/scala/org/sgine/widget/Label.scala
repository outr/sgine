package org.sgine.widget

import com.badlogic.gdx.scenes.scene2d.ui.{Label => GDXLabel, Skin}
import org.sgine.Screen
import org.sgine.component.ActorWidget

class Label(implicit val screen: Screen) extends ActorWidget[GDXLabel] {
  override lazy val actor: GDXLabel = new GDXLabel("", null.asInstanceOf[Skin])
}