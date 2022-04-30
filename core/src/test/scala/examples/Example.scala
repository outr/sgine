package examples

import org.sgine.component.FPSView
import org.sgine.SingleScreenApp

trait Example extends SingleScreenApp {
  override def init(): Unit = {
    super.init()

    FPSView.font @= Fonts.OpenSans.Regular.normal
  }
}