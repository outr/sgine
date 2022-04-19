package org.sgine.update

trait UpdateSupport extends Updatable {
  implicit val updates: Updates = new Updates

  override def update(delta: Double): Unit = updates @= delta
}
