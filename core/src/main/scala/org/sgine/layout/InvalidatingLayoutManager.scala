package org.sgine.layout

import org.sgine.component.{AbstractContainer, Component}
import reactify.Var

trait InvalidatingLayoutManager extends LayoutManager {
  private val dirty: Var[Boolean] = Var(true)

  override def added(container: AbstractContainer, child: Component): Unit = dirty @= true

  override def removed(container: AbstractContainer, child: Component): Unit = dirty @= true

  override def update(container: AbstractContainer, delta: Double): Unit = if (dirty()) {
    dirty @= false

    validate(container: AbstractContainer)
  }

  def validate(container: AbstractContainer): Unit
}
