package org.sgine

sealed trait Clipping

object Clipping {
  case object None extends Clipping
  case object Container extends Clipping
  case class Dimensions(x: Double, y: Double, width: Double, height: Double) extends Clipping
}