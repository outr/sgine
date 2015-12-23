package org.sgine

import org.sgine.event.ActionManager

/**
  * Renderable exposes the render action to be invoked per-render of this element.
  */
trait Renderable {
  /**
    * Fired when visual rendering occurs
    */
  val render = new ActionManager("render")
}
