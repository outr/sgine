package org.sgine.tooltip

import org.sgine.Pointer
import org.sgine.component.{DimensionedComponent, MutableContainer}

trait Tooltip extends DimensionedComponent

object Tooltip extends MutableContainer[Tooltip] {
  override def update(delta: Double): Unit = {
    super.update(delta)

    left @= Pointer.screen.x
    top @= Pointer.screen.y
  }
}