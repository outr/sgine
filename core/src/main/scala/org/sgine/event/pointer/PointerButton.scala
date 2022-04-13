package org.sgine.event.pointer

sealed trait PointerButton {
  def index: Int
}

object PointerButton {
  case object Left extends PointerButton {
    override def index: Int = 0
  }
  case object Right extends PointerButton {
    override def index: Int = 1
  }
  case object Middle extends PointerButton {
    override def index: Int = 2
  }
}