package org.sgine.component

import java.util.concurrent.atomic.AtomicInteger

import org.sgine.{InputSupport, Screen}

trait Component extends InputSupport {
  def screen: Screen

  val id: Int = Component.idGenerator.incrementAndGet()
}

object Component {
  private val idGenerator = new AtomicInteger(0)
}