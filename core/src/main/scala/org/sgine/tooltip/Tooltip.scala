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
    screenOption().foreach { screen =>
      if (Pointer.screen.x() > screen.center()) {
        right @= Pointer.screen.x
      }
      if (Pointer.screen.y() > screen.middle()) {
        bottom @= Pointer.screen.y
      }
    }
  }
}