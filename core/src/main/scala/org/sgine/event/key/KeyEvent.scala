package org.sgine.event.key

import org.sgine.Key
import org.sgine.component.Component
import org.sgine.event.Event

case class KeyEvent(state: KeyState, key: Key, target: Component) extends Event
