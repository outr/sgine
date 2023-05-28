package org.sgine

sealed trait Compass

object Compass {
  case object North extends Compass
  case object South extends Compass
  case object East extends Compass
  case object West extends Compass
}