package org.sgine.component

import org.sgine.event.ActionManager
import reactify._

trait Focusable extends Component {
  lazy val focus: ActionManager = new ActionManager("focused")
  lazy val blur: ActionManager = new ActionManager("blurred")
  val clickToFocus: Var[Boolean] = Var(true)

  mouse.tapped.attach { evt =>
    if (clickToFocus.get) requestFocus()
  }
  focus.on {
    processFocus()
  }

  // TODO: support tab and shift-tab controlled by Sgine instead of libgdx

  def hasFocus: Boolean = screen.focused.get.contains(this)

  def requestFocus(): Unit = if (!screen.focused.get.contains(this)) screen.focused := Some(this)

  protected def processFocus(): Unit = this match {
    case ac: ActorComponent[_] => screen.stage.setKeyboardFocus(ac.actor)
    case _ => screen.stage.setKeyboardFocus(null)
  }
}