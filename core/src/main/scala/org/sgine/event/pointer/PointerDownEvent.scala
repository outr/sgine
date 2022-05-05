package org.sgine.event.pointer

import org.sgine.component.Component

case class PointerDownEvent(displayX: Int,
                            displayY: Int,
                            screenX: Double,
                            screenY: Double,
                            percentX: Double,
                            percentY: Double,
                            target: Component,
                            targetX: Double,
                            targetY: Double,
                            pointer: Int,
                            button: PointerButton,
                            time: Long = System.currentTimeMillis()) extends PointerEvent {
  def reTarget(target: Component, targetX: Double, targetY: Double): PointerDownEvent = copy(
    target = target,
    targetX = targetX,
    targetY = targetY
  )
}
