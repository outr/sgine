package org.sgine

import org.sgine.event.ActionManager

/**
  * RenderFlow represents the visual flow of events of the life cycle of an application. This is used by Screen and UI.
  */
trait RenderFlow extends Renderable {
  /**
    * Create events are invoked upon graphical creation of this element
    */
  val create = new ActionManager("create")
  /**
    * Show events are invoked upon showing of this element
    */
  val show = new ActionManager("show")
  /**
    * Hide events are invoked upon hiding of this element
    */
  val hide = new ActionManager("hide")
  /**
    * Resize events are invoked when this element is resized
    */
  val resize = new ActionManager("resize")
  /**
    * Pause events are invoked when this element is paused
    */
  val pause = new ActionManager("pause")
  /**
    * Resume events are invoked when this element is resumed from a paused state
    */
  val resume = new ActionManager("resume")
  /**
    * Dispose events are invoked when this element should cleanup and terminate
    */
  val dispose = new ActionManager("dispose")
}