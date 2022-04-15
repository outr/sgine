package org.sgine.component

import reactify._

trait Component {
  protected[sgine] val _parent: Var[Option[Component]] = Var(None)

  val parent: Val[Option[Component]] = _parent
}