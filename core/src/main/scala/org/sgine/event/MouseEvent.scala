package org.sgine.event

import org.sgine.component.Component

case class MouseEvent(button: Int, x: Double, y: Double, component: Component) {
  val timestamp: Long = System.currentTimeMillis()
}