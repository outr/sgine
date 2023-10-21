package example

import org.sgine.component._
import org.sgine.drawable.{Drawer, ShapeDrawable}
import org.sgine.event.{Draggable, DraggableSupport}
import org.sgine.{Clipping, Color}
import reactify._

object ClippingExample extends Example {
  private lazy val background = new ShapeDrawable {
    override def draw(drawer: Drawer, delta: Double): Unit = {
      drawer.color = Color.Aero
      drawer.filled.rectangle()
    }

    override def width: Double = 800.0
    override def height: Double = 800.0
  }
  private lazy val label = new Label("Hello, [#00ff00ff]World![]\n\nTesting, this is some [#00ffffff]really long text[] that should wrap multiple lines!") with PointerSupport {
    font @= Fonts.Pacifico.normal
    wrap @= true
    x @= 25.0
    y @= 25.0
    width @= 1000.0

    color := (if (pointer.isOver) Color.Red else Color.White)

    override def toString: String = "text"
  }

  override protected lazy val component: Component = new Container with DraggableSupport {
    width @= 800.0
    height @= 800.0
    center @= screen.center
    middle @= screen.middle
    clipping @= Clipping.Container

    draggable @= Some(Draggable(label, label))
    drag.within(
      container = this,
      padLeft = 25.0,
      padTop = 25.0,
      padRight = 25.0,
      padBottom = 25.0
    )

    override val children: Children[Component] = Children(this, Vector(
      new Image(background),
      label
    ))

    override def toString: String = "clipping-container"
  }
}