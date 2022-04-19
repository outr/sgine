package org.sgine.component

import org.sgine.Screen

class EmptyComponent(implicit val screen: Screen) extends DimensionedComponent() {
  size.width := 0.0
  size.height := 0.0
}
