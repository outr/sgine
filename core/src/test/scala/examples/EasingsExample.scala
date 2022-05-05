package examples

import org.sgine.component.{Component, Image}
import org.sgine.easing.Easing
import org.sgine.task._
import reactify._

import scala.concurrent.duration._

object EasingsExample extends Example {
  override protected lazy val component: Component = new Image("sgine.png") with TaskSupport {
    center @= screen.center
    middle @= screen.middle
    scaleX @= 0.75
    scaleY @= 0.75

    forever(x to 0.0 in 1.seconds easing Easing.bounceOut
      andThen (right to screen.width in 1.seconds easing Easing.sineIn)
      andThen (center to screen.width / 2.0 in 1.seconds easing Easing.elasticOut)
      andThen (rotation to -360.0 in 2.seconds easing Easing.elasticOut)
      andThen synchronous(rotation := 0.0)).start
  }
}
