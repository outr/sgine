package org.sgine

import org.sgine.component.{Component, FPSView, MutableContainer}

object Overlay extends Screen with MutableContainer[Component] {
  locked @= true
  priority @= 1000.0

  override protected def init(): Unit = {
    super.init()

    children += FPSView
  }

  override protected lazy val component: Component = this
}