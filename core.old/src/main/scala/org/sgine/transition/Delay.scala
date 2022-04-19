package org.sgine.transition

import org.sgine._

class Delay(duration: Double) extends Transition {
  private var elapsed: Double = 0.0

  override def init(): Unit = elapsed = 0.0

  override def invoke(): Unit = elapsed += ui.delta

  override def finished: Boolean = elapsed >= duration
}
