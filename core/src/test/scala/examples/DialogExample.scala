package examples

import org.sgine.Color
import org.sgine.component.{Children, Component, Container, Image, Label, PointerSupport, Rectangle}
import org.sgine.dialog.Dialog
import org.sgine.drawable.{Scale9, Texture}
import org.sgine.event.{Draggable, DraggableSupport}
import reactify._

object DialogExample extends Example {
  override protected def component: Component = new Image("sgine.png") with PointerSupport {
    center := screen.center
    middle := screen.middle

    pointer.down.on {
      scribe.info("Showing ExampleDialog!")
      ExampleDialog.visible @= !ExampleDialog.visible
    }
  }

  object ExampleDialog extends Container with PointerSupport with Dialog with DraggableSupport { dialog =>
    private lazy val scale9 = Scale9(Texture.internal("scale9test.png"), 50, 50, 50, 50)
    private lazy val topBar = new Rectangle with PointerSupport {
      color @= Color.DarkGreen
      width @= 750.0
      height @= 100.0
    }
    private lazy val title = new Label {
      font @= Fonts.OpenSans.Regular.normal
      x @= 15.0
      y @= 5.0
      text @= "Example Dialog"
    }
    private lazy val body = new Image(scale9) {
      y @= 100.0
      width @= 750.0
      height @= 400.0
    }

    center := screen.center
    middle := screen.middle

    draggable @= Some(Draggable(topBar, this))
    drag.boundTo(screen)

    override def children: Children[Component] = Children(this, Vector(
      topBar,
      title,
      body
    ))
  }
}
