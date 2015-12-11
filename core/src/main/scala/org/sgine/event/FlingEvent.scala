package org.sgine.event

import org.sgine.component.Component

case class FlingEvent(x: Double, y: Double, button: Int, velocityX: Double, velocityY: Double, component: Component) {
  val timestamp: Long = System.currentTimeMillis()
}