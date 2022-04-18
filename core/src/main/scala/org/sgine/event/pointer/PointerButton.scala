package org.sgine.event.pointer

class PointerButton(index: Int)

object PointerButton {
  lazy val Left: PointerButton = new PointerButton(0)
  lazy val Right: PointerButton = new PointerButton(1)
  lazy val Middle: PointerButton = new PointerButton(2)

  def apply(index: Int): PointerButton = if (index == 0) {
    Left
  } else if (index == 1) {
    Right
  } else if (index == 2) {
    Middle
  } else {
    new PointerButton(index)
  }
}