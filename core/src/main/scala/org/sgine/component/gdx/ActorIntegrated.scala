package org.sgine.component.gdx

import org.sgine.Renderable
import org.sgine.component.Component
import org.sgine.event.ActionManager

trait ActorIntegrated extends Renderable with Component {
  val act = new ActionManager("act")
}
