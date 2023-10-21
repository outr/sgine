package example

import org.sgine.Color
import org.sgine.component.{Component, Image}
import org.sgine.task._
import reactify._

import scala.concurrent.duration._

object AnimationColorsExample extends Example {
  override protected def component: Component = new Image("sgine.png") with TaskSupport {
    center @= screen.center
    middle @= screen.middle

    forever(
      color to nextColor() in 1.second
    ).start
  }

  private def nextColor(): Color = Color.random
}
