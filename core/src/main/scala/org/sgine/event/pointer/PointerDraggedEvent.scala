package org.sgine.event.pointer

import org.sgine.component.Component

case class PointerDraggedEvent(displayX: Int,
                               displayY: Int,
                               screenX: Double,
                               screenY: Double,
                               deltaX: Double,
                               deltaY: Double,
                               percentX: Double,
                               percentY: Double,
                               target: Component,
                               targetX: Double,
                               targetY: Double,
                               pointer: Int,
                               finished: Boolean,
                               time: Long = System.currentTimeMillis()) extends PointerEvent {
  def reTarget(target: Component, targetX: Double, targetY: Double): PointerDraggedEvent = copy(
    target = target,
    targetX = targetX,
    targetY = targetY
  )
}
