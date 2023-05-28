package org.sgine.layout

import org.sgine.component.AbstractContainer
import reactify._

trait LayoutSupport extends AbstractContainer {
  val layout: Var[LayoutManager] = Var(LayoutManager.None)

  children.added.attach(c => layout().added(this, c))
  children.removed.attach(c => layout().removed(this, c))
  updates.attach(delta => layout().update(this, delta))
}