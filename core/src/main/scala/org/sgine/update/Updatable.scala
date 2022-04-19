package org.sgine.update

trait Updatable {
  def update(delta: Double): Unit
}
