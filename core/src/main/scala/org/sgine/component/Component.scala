package org.sgine.component

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
}