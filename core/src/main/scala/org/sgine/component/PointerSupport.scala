package org.sgine.component

import org.sgine.event.pointer.PointerEvents
import reactify._

trait PointerSupport extends DimensionedSupport {
  lazy val pointer: PointerEvents = new PointerEvents
}
