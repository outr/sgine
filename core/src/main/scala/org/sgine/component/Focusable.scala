package org.sgine.component

import org.sgine.event.ActionManager

trait Focusable extends Component {
  lazy val focus: ActionManager = new ActionManager("focused")
  lazy val blur: ActionManager = new ActionManager("blurred")

  def hasFocus: Boolean = screen.focused.get.contains(this)

  def requestFocus(): Unit = screen.focused := Some(this)

  protected[sgine] def applyFocus(): Unit

  protected[sgine] def applyBlur(): Unit
}
