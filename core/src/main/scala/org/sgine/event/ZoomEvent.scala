package org.sgine.event

import org.sgine.component.Component

case class ZoomEvent(x: Double, y: Double, originalDistance: Double, currentDistance: Double, component: Component) {
  val timestamp: Long = System.currentTimeMillis()
}
