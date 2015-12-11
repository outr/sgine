package org.sgine.event

import org.sgine.component.Component
import org.sgine.input.Key

case class KeyEvent(key: Key, focused: Option[Component], atCursor: Option[Component]) {
  val timestamp: Long = System.currentTimeMillis()
}