package examples

import org.sgine.audio.Audio
import org.sgine.{Screen, SgineApp, UI}
import org.sgine.component.{Component, FPSView}

import java.io.File

trait Example extends SgineApp { self =>
  object screen extends Screen {
    override protected def component: Component = self.component
  }

  protected def component: Component

  override protected def init(): Unit = {
    FPSView.font @= Fonts.OpenSans.Regular.normal
    Audio.resourcesDirectory = Some(new File("src/test/resources"))

    UI.screens @= screen
  }
}