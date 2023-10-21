package example

import org.sgine.Color
import org.sgine.component.{Component, Image}
import org.sgine.drawable.{Drawer, ShapeDrawable, Texture}
import org.sgine.task._
import reactify._

import scala.concurrent.duration.DurationInt

object ShapesExample extends Example {
  private lazy val texture = Texture.internal("crate.jpg")

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
      override def draw(drawer: Drawer, delta: Double): Unit = {
        drawer.color = Color.Red
        drawer.filled.rectangle()
        drawer.color = Color.DarkRed
        drawer.rectangle(100.0, 100.0, drawer.width - 200.0, drawer.height - 200.0, lineWidth = 20.0)
        drawer.color = Color.Blue
        drawer.line(width + 100.0, -100.0, -100.0, height + 100.0, 10.0)
        drawer.line(-100.0, -100.0, width + 100.0, height + 100.0, 10.0)
        drawer.filled.sector(
          x = 300.0,
          y = 300.0,
          radius = 400.0,
          startAngle = 0.0,
          degrees = 45,
          innerColor = Color.Green.withAlpha(0.5),
          outerColor = Color.Blue.withAlpha(0.5)
        )
//        drawer.draw(texture)()
      }

      override def width: Double = 1000.0

      override def height: Double = 1000.0
    }
  }
}
