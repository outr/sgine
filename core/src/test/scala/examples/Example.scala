package examples

import org.sgine.SingleScreenApp
import org.sgine.component.FPSView

trait Example extends SingleScreenApp {
  override def init(): Unit = {
    super.init()

    FPSView.font @= Fonts.OpenSans.Regular.normal
  }
}