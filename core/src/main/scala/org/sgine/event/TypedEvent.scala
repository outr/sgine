package org.sgine.event

import org.sgine.component.Component

case class TypedEvent(char: Char, target: Component) extends Event