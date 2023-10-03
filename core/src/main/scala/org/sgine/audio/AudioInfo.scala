package org.sgine.audio

import fabric.rw._

case class AudioInfo(length: Double)

object AudioInfo {
  implicit val rw: RW[AudioInfo] = RW.gen
}