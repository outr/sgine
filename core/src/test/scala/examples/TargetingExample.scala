package examples

import org.sgine.{Color, Pointer}
import org.sgine.component.{Children, Component, Container, Image, Label, MutableContainer}
import perfolation.double2Implicits
import reactify.Val

import scala.util.Random

object TargetingExample extends Example {
  // TODO: outline on component showing targetting range
  // TODO: draw line to nearest target if in range

  private lazy val player = new Image("basketball.png") {
    center := Pointer.screen.x
    middle := Pointer.screen.y
  }

  private lazy val enemies = (0 until 10).toList.map(_ => new Enemy)
  private lazy val nearest = Val(enemies.sortBy(_.distance()))

  override protected lazy val component: Component = new MutableContainer[Component] {
    width := screen.width
    height := screen.height

    children += player
    children ++= enemies
  }

  class Enemy extends Container { e =>
    private val r1 = Random.nextDouble()
    private val r2 = Random.nextDouble()

    center := r1 * screen.width
    middle := r2 * screen.height
    width @= 200.0
    height @= 200.0

    val distance: Val[Double] = Val[Double] {
      val px = Pointer.screen.x()
      val py = Pointer.screen.y()
      val ex = e.center()
      val ey = e.middle()
      math.sqrt(math.pow(ex - px, 2.0) + math.pow(ey - py, 2.0))
    }

    private lazy val image = new Image("crate.jpg") {
      width @= 200.0
      height @= 200.0
      color := (if (nearest().head eq e) Color.Red else Color.White)
    }

    private lazy val text = new Label("Testing") {
      font @= Fonts.OpenSans.Regular.small
      color @= Color.White
      center @= 100.0
      middle @= 100.0

      this.text := s"d: ${distance().f()}"
    }

    override lazy val children: Children[Component] = Children(
      this, Vector(image, text)
    )
  }
}
