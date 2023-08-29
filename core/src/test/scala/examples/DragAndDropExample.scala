package examples

import org.sgine.Color
import org.sgine.component.{Component, Container, DimensionedSupport, Image}
import org.sgine.dnd.{DragAndDropSupport, DropSupport}

import reactify._

object DragAndDropExample extends Example {
  private object Ball extends Image("basketball.png") with DragAndDropSupport {
    override type Drag = Image

    center := screen.center * 0.5
    middle := screen.middle

    override protected def createDragComponent(): Image = {
      val img = new Image("basketball.png")
      img.color @= Color.White.withAlpha(0.5)
      img
    }

    override protected def out(drag: Image, drop: DropSupport, accept: Boolean): Unit = {
      drag.color @= Color.White.withAlpha(0.5)
    }

    override protected def over(drag: Image, drop: DropSupport, accept: Boolean): Unit = if (accept) {
      drag.color @= Color.White
    } else {
      drag.color @= Color.White.withAlpha(0.5)
    }

    override protected def snapTo(drag: Image, drop: DropSupport): Unit = {
      drag.x @= drop.x
      drag.y @= drop.y
    }
  }

  private class Crate(accept: Boolean) extends Image("crate.jpg") with DropSupport {
    override def dragSnap: Boolean = true

    override def accepts(c: DragAndDropSupport): Boolean = accept

    override def over(c: DragAndDropSupport, accept: Boolean): Unit = color @= (if (accept) Color.Blue else Color.Red)

    override def out(c: DragAndDropSupport, accept: Boolean): Unit = color @= Color.White

    override def receive(c: DragAndDropSupport, accept: Boolean): Unit = color @= (if (accept) Color.Green else Color.Red)
  }

  private object AcceptCrate extends Crate(accept = true) {
    center := screen.center * 1.5
    middle := screen.middle
  }

  private object RejectCrate extends Crate(accept = false) {
    center := screen.center
    middle := screen.middle
  }

  override protected lazy val component: Component = Container(
    Ball, RejectCrate, AcceptCrate
  )
}