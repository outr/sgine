package org.sgine.component

import com.badlogic.gdx.math.Vector3
import org.sgine.event.pointer.PointerEvents
import reactify._

trait InteractiveComponent extends DimensionedComponent {
  lazy val interactive: Var[Boolean] = Var(true)
  lazy val pointer: PointerEvents = new PointerEvents

  def hitTest(v: Vector3): Boolean = {
//    updateHitVector(v)
    if (v.x >= 0.0f && v.x <= width.toFloat && v.y >= 0.0f && v.y <= height.toFloat) {
      true
    } else {
      false
    }
  }
}
