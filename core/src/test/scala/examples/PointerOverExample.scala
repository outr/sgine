package examples

import org.sgine.{Color, PointerCursor}
import org.sgine.component.{Children, Component, Container, Image, PointerSupport}
import org.sgine.task._
import reactify._

import scala.concurrent.duration.DurationInt

object PointerOverExample extends Example {
  override protected lazy val component: Component = new Container { self =>
    override lazy val children: Children[Component] = Children(
      self,
      Vector(
        new Crate
      )
    )
  }

  class Crate extends Image("crate.jpg") with PointerSupport {
    color := (if (pointer.isOver) Color.Red else Color.White)
    pointer.cursor @= Some(PointerCursor.Hand)
    middle := screen.middle

    forever(
      sequential(
        right to screen.width in 5.seconds,
        left to 0.0 in 5.seconds
      )
    ).start
  }
}