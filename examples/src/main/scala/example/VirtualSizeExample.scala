package example

import org.sgine._
import org.sgine.component.{Component, Container, Image}
import reactify._

object VirtualSizeExample extends Example with VirtualSizeSupport {
  override protected lazy val component: Component = Container(
    new Image("1024.jpg") {
      x := 0.0.vx
      y := 0.0.vy
      width := 1024.0.vw
      height := 768.0.vh
    },
    new Image("sgine.png") {
      center := 512.0.vx
      middle := 385.0.vy
    }
  )
}