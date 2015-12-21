package org.sgine

import org.sgine.event.ActionManager

trait RenderFlow extends Renderable {
  val create = new ActionManager("create")
  val show = new ActionManager("show")
  val hide = new ActionManager("hide")
  val resize = new ActionManager("resize")
  val pause = new ActionManager("pause")
  val resume = new ActionManager("resume")
  val dispose = new ActionManager("dispose")
}