package org.sgine.widget

import org.sgine.Screen
import org.sgine.component.Component

class Container(implicit scrn: Screen) extends ComponentGroup()(scrn) {
  def +=(child: Component) = add(child)
  def -=(child: Component) = remove(child)
}
