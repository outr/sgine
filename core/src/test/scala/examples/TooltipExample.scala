package examples

import org.sgine.{Overlay, Pointer}
import org.sgine.component.{Children, Component, Container, DimensionedComponent, Image, InteractiveComponent, Label, MutableContainer, TypedContainer}
import reactify._

object TooltipExample extends Example {
  private lazy val logo = new Image("sgine.png") with TooltipSupport {
    lazy val tooltip: Tooltip = new Label("Logo") with Tooltip {
      font @= Fonts.Pacifico.normal
    }

    center := screen.center
    bottom := screen.middle - 200.0
  }
  private lazy val basketball = new Image("basketball.png") with TooltipSupport {
    lazy val tooltip: Tooltip = new Label("Basketball") with Tooltip {
      font @= Fonts.Pacifico.normal
    }

    center := screen.center
    middle := screen.middle
  }
  private lazy val crate = new Image("crate.jpg") with TooltipSupport {
    lazy val tooltip: Tooltip = new Label("Crate") with Tooltip {
      font @= Fonts.Pacifico.normal
    }

    center := screen.center
    top := screen.middle + 200.0
  }

  override protected def init(): Unit = {
    super.init()

    Overlay.children += Tooltip
  }

  override protected lazy val component: Component = Container(
    logo,
    basketball,
    crate
  )
}