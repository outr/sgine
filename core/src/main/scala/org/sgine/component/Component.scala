package org.sgine.component

import org.sgine.Screen
import reactify._

trait Component {
  protected[sgine] val _parent: Var[Option[Component]] = Var(None)

  /**
   * Visibility flag for this component. Defaults to true.
   */
  val visible: Var[Boolean] = Var(true)

  /**
   * Represents the visibility state of this component. Only represents true if all parent components are also visible.
   */
  lazy val isVisible: Val[Boolean] = Val(visible && parent().forall(_.isVisible()))

  /**
   * Parent component for this component.
   */
  val parent: Val[Option[Component]] = _parent

  lazy val screenOption: Val[Option[Screen]] = Val(parent() match {
    case Some(s: Screen) => Some(s)
    case Some(p: Component) => p.screenOption
    case None => None
  })
}