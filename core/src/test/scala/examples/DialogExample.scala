package examples

import org.sgine.Color
import org.sgine.component.{Children, Component, Container, Image, Label, PointerSupport, Rectangle}
import org.sgine.dialog.Dialog
import org.sgine.drawable.{Scale9, Texture}
import org.sgine.event.{Draggable, DraggableSupport}
import reactify._

// TODO: GlassPane support
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
    private lazy val topScale9 = Scale9(Texture.internal("blue_dialog_bar.png"), 22, 22, 18, 1)
    private lazy val bodyScale9 = Scale9(Texture.internal("blue_dialog_body.png"), 28, 28, 5, 40)
    private lazy val topBar = new Image(topScale9) with PointerSupport {
      width @= 750.0
      height @= 100.0
    }
    private lazy val title = new Label {
      font @= Fonts.OpenSans.Regular.normal
      x @= 25.0
      y @= 5.0
      text @= "Example Dialog"
    }
    private lazy val close = new Image("close.png") with PointerSupport {
      x @= 600.0
      y @= -50.0
      scaleX @= 0.4
      scaleY @= 0.4
      color := (if (pointer.over) Color.Red else Color.DarkRed)
      pointer.down.on(dialog.visible @= false)
    }
    private lazy val body = new Image(bodyScale9) {
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
      close,
      title,
      body
    ))
  }
}
