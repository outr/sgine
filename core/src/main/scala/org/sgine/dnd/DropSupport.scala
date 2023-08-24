package org.sgine.dnd

import org.sgine.component.{DimensionedSupport, PointerSupport}

trait DropSupport extends DimensionedSupport with PointerSupport {
  def accepts(c: DragAndDropSupport): Boolean

  def over(c: DragAndDropSupport): Unit

  def out(c: DragAndDropSupport): Unit

  def receive(c: DragAndDropSupport): Unit
}
