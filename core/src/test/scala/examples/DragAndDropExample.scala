package examples

import org.sgine.Color
import org.sgine.component.{Component, Container, DimensionedSupport, Image}
import org.sgine.dnd.{DragAndDropSupport, DropSupport}

object DragAndDropExample extends Example {
  private object Ball extends Image("basketball.png") with DragAndDropSupport {
    center := screen.center * 0.5
    middle := screen.middle

    override protected def createDragComponent(): DimensionedSupport = {
      val img = new Image("basketball.png")
      img.color @= Color.White.withAlpha(0.5)
      img
    }
  }

  private object Crate extends Image("crate.jpg") with DropSupport {
    center := screen.center * 1.5
    middle := screen.middle

    override def accepts(c: DragAndDropSupport): Boolean = true

    override def over(c: DragAndDropSupport): Unit = color @= Color.Blue

    override def out(c: DragAndDropSupport): Unit = color @= Color.White

    override def receive(c: DragAndDropSupport): Unit = color @= Color.Green
  }

  override protected lazy val component: Component = Container(
    Ball, Crate
  )
}