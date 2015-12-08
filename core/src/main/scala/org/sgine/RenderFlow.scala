package org.sgine

trait RenderFlow {
  val create = new ActionManager
  val show = new ActionManager
  val render = new ActionManager
  val hide = new ActionManager
  val resize = new ActionManager
  val pause = new ActionManager
  val resume = new ActionManager
  val dispose = new ActionManager
}
