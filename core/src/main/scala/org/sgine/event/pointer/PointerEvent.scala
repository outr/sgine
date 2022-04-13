package org.sgine.event.pointer

import org.sgine.component.Component
import org.sgine.event.Event

trait PointerEvent extends Event {
  def displayX: Int

  def displayY: Int

  def screenX: Double

  def screenY: Double

  def percentX: Double

  def percentY: Double

  def target: Component

  def targetX: Double

  def targetY: Double

  def within(x: Double, y: Double, width: Double, height: Double): Boolean =
    screenX >= x && screenX <= x + width && screenY >= y && screenY <= y + height

  def reTarget(target: Component, targetX: Double, targetY: Double): PointerEvent
}
