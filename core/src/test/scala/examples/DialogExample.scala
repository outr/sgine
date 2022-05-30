package examples

import org.sgine.component.{Children, Component, Container, Image, PointerSupport}
import org.sgine.dialog.Dialog
import org.sgine.drawable.{Scale9, Texture}
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

  object ExampleDialog extends Container with Dialog { dialog =>
    private lazy val scale9 = Scale9(Texture.internal("scale9test.png"), 50, 50, 50, 50)
    private lazy val body = new Image(scale9) {
      width := dialog.width
      height := dialog.height
    }

    center := screen.center
    middle := screen.middle
    width @= 750.0
    height @= 400.0

    override def children: Children[Component] = Children(this, Vector(
      body
    ))
  }
}
