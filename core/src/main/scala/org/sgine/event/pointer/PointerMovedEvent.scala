package org.sgine.event.pointer

import org.sgine.component.Component

case class PointerMovedEvent(displayX: Int,
                             displayY: Int,
                             screenX: Double,
                             screenY: Double,
                             percentX: Double,
                             percentY: Double,
                             target: Component,
                             targetX: Double,
                             targetY: Double) extends PointerEvent {
  def reTarget(target: Component, targetX: Double, targetY: Double): PointerMovedEvent = copy(
    target = target,
    targetX = targetX,
    targetY = targetY
  )
}
