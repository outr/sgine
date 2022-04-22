package examples

import org.sgine.component.{Component, Image}
import org.sgine.easing.Easing
import org.sgine.task._
import reactify._

import scala.concurrent.duration.DurationInt

object AnimationExample extends Example {
  override protected lazy val root: Component = new Image("sgine.png") with TaskSupport {
    center @= screen.center
    middle @= screen.middle

    forever(
      sequential(
        parallel(
          rotation to 360.0 in 2.seconds easing Easing.elasticOut,
          scaleX to 0.2 in 2.seconds,
          scaleY to 0.2 in 2.seconds
        ),
        parallel(
          rotation to 0.0 in 2.seconds easing Easing.elasticOut,
          scaleX to 2.0 in 2.seconds,
          scaleY to 2.0 in 2.seconds
        )
      )
    ).start
  }
}