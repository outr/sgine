package example

import org.sgine.component.{Component, Container, Image, Label}
import org.sgine.task._
import org.sgine.tooltip.{Tooltip, TooltipSupport}
import reactify._

import scala.concurrent.duration.DurationInt

object TooltipExample extends Example {
  private lazy val logo = new Image("sgine.png") with TooltipSupport {
    tooltip @= Some(new Label("Logo") with Tooltip {
      font @= Fonts.OpenSans.Regular.extraLarge
    })

    center := screen.center
    bottom := screen.middle - 200.0
  }
  private lazy val basketball = new Image("basketball.png") with TooltipSupport {
    tooltip @= Some(new Label("Basketball") with Tooltip {
      font @= Fonts.Pacifico.normal
    })

    center := screen.center
    middle := screen.middle
  }
  private lazy val crate = new Image("crate.jpg") with TooltipSupport {
    tooltip @= Some(new Label("Crate") with Tooltip {
      font @= Fonts.Pacifico.normal
    })
    tooltip.clickLock @= true

    center := screen.center
    top := screen.middle + 200.0

    forever(
      sequential(
        right to screen.right in 5.seconds,
        left to screen.left in 5.seconds
      )
    ).start
  }

  override protected lazy val component: Component = Container(
    logo,
    basketball,
    crate
  )
}