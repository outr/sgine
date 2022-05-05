package examples

import org.sgine.{Screen, SgineApp, UI}
import org.sgine.component.{Component, FPSView}

trait Example extends SgineApp { self =>
  protected object screen extends Screen {
    override protected def component: Component = self.component
  }

  protected def component: Component

  override protected def init(): Unit = {
    FPSView.font @= Fonts.OpenSans.Regular.normal

    UI.screen @= screen
  }
}