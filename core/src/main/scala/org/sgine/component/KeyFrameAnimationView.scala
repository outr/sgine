package org.sgine.component

import org.sgine.drawable.Texture
import org.sgine.update.Updatable
import reactify._

class KeyFrameAnimationView(textures: Vector[Texture]) extends Image(textures(0)) with Updatable {
  val index: Var[Int] = Var(0)
  val speed: Var[Double] = Var(0.05)
  val paused: Var[Boolean] = Var(false)

  private var elapsed = 0.0

  drawable := textures(index)

  override def update(delta: Double): Unit = if (!paused() && speed() != 0.0) {
    elapsed += delta
    if (elapsed >= speed) {
      val newIndex = index + 1
      if (newIndex >= textures.length) {
        index @= 0
      } else {
        index @= newIndex
      }
      elapsed = 0.0
    }
  }
}