package org.sgine.audio

case class AudioInfo(length: Double)

object AudioInfo {
  implicit val rw: RW[AudioInfo] = RW.gen
}