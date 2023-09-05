package org.sgine.audio

import org.sgine.update.UpdateSupport

trait AudioElement extends UpdateSupport {
  def dispose(): Unit = Audio.remove(this)
}