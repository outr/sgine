package org.sgine.component

import org.sgine.update.Updatable
import reactify.Channel

trait AnimationSupport extends Updatable {
  val delta: Channel[Double] = Channel[Double]

  override def update(delta: Double): Unit = this.delta @= delta
}