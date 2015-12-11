package org.sgine.event

import com.badlogic.gdx.math.Vector2
import org.sgine.component.Component

case class PinchEvent(x: Double,
                      y: Double,
                      initialFirstPointer: Vector2,
                      initialSecondPointer: Vector2,
                      firstPointer: Vector2,
                      secondPointer: Vector2,
                      component: Component) {
  val timestamp: Long = System.currentTimeMillis()
}
