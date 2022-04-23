package examples

import org.sgine.component.{Component, DimensionedComponent, Image, InteractiveComponent, MutableContainer}
import org.sgine.task._
import reactify._

object ContainerExample extends Example with TaskSupport {
  private lazy val crate1 = new Image("crate.jpg") {
    override def toString: String = "crate1"
  }
  private lazy val crate2 = new Image("crate.jpg") {
    x := width
    y := height

    override def toString: String = "crate2"
  }
  private lazy val container = new MutableContainer[Component] with DimensionedComponent with InteractiveComponent {
    private var index = 0
    private val actions = Vector(
      () => {
        center := screen.center
        middle := screen.middle
        rotation @= 0.0
      },
      () => {
        right := screen.width
        bottom := screen.height
      },
      () => {
        center := screen.center
        middle := screen.middle
        rotation @= 45.0
      }
    )

    children.add(crate1)
    children.add(crate2)

    center := screen.center
    middle := screen.middle

    pointer.down.on {
      index += 1
      if (index >= actions.length) {
        index = 0
      }
      actions(index)()
    }

    override def toString: String = "container"
  }

  override protected lazy val root: Component = container
}