package org.sgine

import org.sgine.event.ActionManager

trait Renderable {
  val render = new ActionManager("render")
}
