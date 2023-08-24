package examples

import org.sgine.{Color, Overlay, Pointer}
import org.sgine.component.{Component, Container, DimensionedSupport, Image, PointerSupport}

object DragAndDropExample extends Example {
  private object Ball extends Image("basketball.png") with DragAndDropSupport {
    center := screen.center
    middle := screen.middle

    override protected def createDragComponent(): DimensionedSupport = {
      val img = new Image("basketball.png")
      img.color @= Color.White.withAlpha(0.5)
      img
    }
  }

  override protected lazy val component: Component = Container(
    Ball
  )
}

trait DragAndDropSupport extends DimensionedSupport with PointerSupport {
  private var dragging: Option[DimensionedSupport] = None

  pointer.draggable @= true
  pointer.dragged.attach { evt =>
    if (dragging.isEmpty) {
      scribe.info("CREATING!")
      val c = createDragComponent()
      c.center := Pointer.screen.x
      c.middle := Pointer.screen.y
      Overlay.children += c
      dragging = Some(c)
    }
    val d = dragging.get
    if (evt.finished) {
      d.removeFromParent()
      dragging = None
    }
  }
  protected def createDragComponent(): DimensionedSupport
}