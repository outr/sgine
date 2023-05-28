package org.sgine.layout

import org.sgine.component.{AbstractContainer, Component}

trait LayoutManager {
  def added(container: AbstractContainer, child: Component): Unit = {}
  def removed(container: AbstractContainer, child: Component): Unit = {}
  def update(container: AbstractContainer, delta: Double): Unit = {}
}

object LayoutManager {
  case object None extends LayoutManager
}