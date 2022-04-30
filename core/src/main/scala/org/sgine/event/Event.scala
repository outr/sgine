package org.sgine.event

import org.sgine.component.Component

trait Event {
  def target: Component

  def time: Long
}