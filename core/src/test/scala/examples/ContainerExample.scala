package examples

import org.sgine.Color
import org.sgine.component._
import org.sgine.task._
import reactify._

import scala.concurrent.duration.DurationInt

object ContainerExample extends Example with TaskSupport {
  private lazy val crate1 = new Image("crate.jpg") {
    override def toString: String = "crate1"
  }
  private lazy val crate2 = new Image("crate.jpg") {
    x := width
    y := height

    override def toString: String = "crate2"
  }
  private lazy val container = new MutableContainer[Component] with DimensionedSupport with PointerSupport {
    color := (if (pointer.isOver) Color.Red else Color.White)

    private var index = 0
    private val time = 250.millis
    private val actions = Vector(
      () => {
        parallel(
          center to screen.center in time,
          middle to screen.middle in time,
          rotation to 0.0 in time
        ).start
      },
      () => {
        parallel(
          right to screen.width in time,
          bottom to screen.height in time
        ).start
      },
      () => {
        parallel(
          center to screen.center in time,
          middle to screen.middle in time,
          rotation to 135.0 in time
        ).start
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

  override protected lazy val component: Component = container
}