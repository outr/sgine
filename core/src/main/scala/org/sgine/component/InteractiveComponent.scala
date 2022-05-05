package org.sgine.component

import org.sgine.event.pointer.PointerEvents
import reactify._

trait InteractiveComponent extends DimensionedComponent {
  lazy val interactive: Var[Boolean] = Var(true)
  lazy val pointer: PointerEvents = new PointerEvents
}
