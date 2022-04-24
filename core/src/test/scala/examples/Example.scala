package examples

import org.sgine.{SingleScreenApp, UI}

trait Example extends SingleScreenApp {
  override def init(): Unit = {
    super.init()

    UI.fpsFont @= OpenSans.Regular.normal
  }
}