package examples

import org.sgine.{Color, Pointer}
import org.sgine.component.{Children, Component, Container, Image, Label, MutableContainer, Targeting}
import org.sgine.drawable.{Drawer, ShapeDrawable}
import org.sgine.util.MathUtils
import perfolation.double2Implicits
import reactify._

import scala.util.Random

object TargetingExample extends Example {
  private lazy val enemies = (0 until 10).toList.map(_ => new Enemy)
  private lazy val nearest = Val(enemies.sortBy(_.distance()))

  override protected lazy val component: Component = new MutableContainer[Component] {
    width := screen.width
    height := screen.height

    children += Player
    children ++= enemies
  }

  object Player extends Container with Targeting[Enemy] {
    center := Pointer.screen.x
    middle := Pointer.screen.y
    width @= 220.0
    height @= 220.0

    override protected def maxDistance: Double = 500.0

    override protected def targets: List[Enemy] = enemies

    private lazy val image = new Image("basketball.png")
    private lazy val drawing = new Image {
      drawable @= shape
    }

    object shape extends ShapeDrawable {
      override def draw(drawer: Drawer): Unit = {
        drawer.color = Color.White
        drawer.circle(110.0, 110.0, 500.0, 6.0)
        currentTarget().foreach { target =>
          val x = global.localizeX(target.global.center)
          val y = global.localizeY(target.global.middle)
          drawer.color = Color.Green
          drawer.line(110.0, 110.0, x, y, 6.0)
          val (fx, fy) = MathUtils.changeLineLength(110.0, 110.0, x, y, 1000.0)
          drawer.color = Color.Blue
          drawer.line(110.0, 110.0, fx, fy, 6.0)
          enemies.foreach { e =>
            val gx1 = global.center
            val gy1 = global.middle
            val gx2 = global.x + fx
            val gy2 = global.y + fy
            val intersecting = MathUtils.intersectSegmentCircle(
              x1 = gx1,
              y1 = gy1,
              x2 = gx2,
              y2 = gy2,
              circleX = e.global.center,
              circleY = e.global.middle,
              circleRadius = 110.0
            )
            e.intersecting @= intersecting
          }
        }
      }

      override def width: Double = 220.0
      override def height: Double = 220.0
    }

    override lazy val children: Children[Component] = Children(
      this, Vector(image, drawing)
    )
  }

  class Enemy extends Container { e =>
    private val r1 = Random.nextDouble()
    private val r2 = Random.nextDouble()

    val intersecting: Var[Boolean] = Var(false)

    center := r1 * screen.width
    middle := r2 * screen.height
    width @= 200.0
    height @= 200.0

    val distance: Val[Double] = Val[Double](MathUtils.distanceFromCenter(Player, e))

    private lazy val image = new Image("crate.jpg") {
      width @= 200.0
      height @= 200.0
      color := {
        if (nearest().head eq e) {
          if (distance() <= 500.0) {
            Color.Green
          } else {
            Color.Red
          }
        } else if (intersecting()) {
          Color.Blue
        } else {
          Color.White
        }
      }
    }

    private lazy val text = new Label {
      font @= Fonts.OpenSans.Regular.small
      color @= Color.White
      center @= 100.0
      middle @= 100.0

      this.text := distance().f()
    }

    override lazy val children: Children[Component] = Children(
      this, Vector(image, text)
    )
  }
}