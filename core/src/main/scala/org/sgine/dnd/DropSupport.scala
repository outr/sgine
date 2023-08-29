package org.sgine.dnd

import org.sgine.component.{DimensionedSupport, PointerSupport}

trait DropSupport extends DimensionedSupport with PointerSupport {
  def accepts(c: DragAndDropSupport): Boolean

  def over(c: DragAndDropSupport, accept: Boolean): Unit

  def out(c: DragAndDropSupport, accept: Boolean): Unit

  def receive(c: DragAndDropSupport, accept: Boolean): Unit
}
