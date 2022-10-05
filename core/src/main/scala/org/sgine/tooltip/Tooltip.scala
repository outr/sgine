package org.sgine.tooltip

import org.sgine.{Overlay, Pointer}
import org.sgine.component.{DimensionedSupport, MutableContainer}
import reactify._

trait Tooltip extends DimensionedSupport

object Tooltip extends MutableContainer[Tooltip] {
  Overlay.children += this

  override def update(delta: Double): Unit = {
    super.update(delta)

    left @= Pointer.screen.x
    top @= Pointer.screen.y
  }
}