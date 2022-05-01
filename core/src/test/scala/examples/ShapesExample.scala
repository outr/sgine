package examples

import org.sgine.Color
import org.sgine.component.{Component, DimensionedComponent, Image}
import org.sgine.drawable.ShapeDrawable
import space.earlygrey.shapedrawer.{JoinType, ShapeDrawer}
import reactify._

object ShapesExample extends Example {
  override protected lazy val component: Component = new Image {
    center := screen.center
    middle := screen.middle

    drawable @= new ShapeDrawable {
      override def draw(drawer: ShapeDrawer, x: Double, y: Double): Unit = {
        drawer.setColor(Color.Red.gdx)
        drawer.filledRectangle(x.toFloat, y.toFloat, 1000.0f, 1000.0f)
        drawer.rectangle(x.toFloat + 100.0f, y.toFloat + 100.0f, (width - 200.0).toFloat, (height - 200.0).toFloat, Color.DarkRed.gdx, 20.0f)
      }

      override def width: Double = 1000.0

      override def height: Double = 1000.0
    }
  }
}
