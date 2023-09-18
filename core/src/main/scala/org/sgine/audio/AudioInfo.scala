package org.sgine.audio

import fabric.rw.RW

case class AudioInfo(length: Double)

object AudioInfo {
  implicit val rw: RW[AudioInfo] = RW.gen
}