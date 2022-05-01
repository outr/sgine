package examples

import org.sgine.Color
import org.sgine.component.{Component, Image}
import org.sgine.drawable.{Drawer, ShapeDrawable}
import org.sgine.task._
import reactify._

import scala.concurrent.duration.DurationInt

object ShapesExample extends Example {
  override protected lazy val component: Component = new Image {
    center := screen.center
    middle := screen.middle

    forever(
      sequential(
        rotation to 360.0 in 2.seconds,
        synchronous(rotation @= 0.0)
      )
    ).start

    drawable @= new ShapeDrawable {
      override def draw(drawer: Drawer): Unit = {
        drawer.color = Color.Red
        drawer.filled.rectangle()
        drawer.color = Color.DarkRed
        drawer.rectangle(100.0, 100.0, drawer.width - 200.0, drawer.height - 200.0, lineWidth = 20.0)
        drawer.color = Color.Blue
        drawer.line(width + 100.0, -100.0, -100.0, height + 100.0, 10.0)
        drawer.line(-100.0, -100.0, width + 100.0, height + 100.0, 10.0)
      }

      override def width: Double = 1000.0

      override def height: Double = 1000.0
    }
  }
}
