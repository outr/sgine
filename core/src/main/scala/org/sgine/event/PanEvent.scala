package org.sgine.event

import org.sgine.component.Component

case class PanEvent(x: Double, y: Double, deltaX: Double, deltaY: Double, component: Component) {
  val timestamp: Long = System.currentTimeMillis()
}
