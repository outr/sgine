package example

import org.sgine.audio.Audio
import org.sgine.component.{Component, FPSView}
import org.sgine.lwjgl.DesktopLauncher
import org.sgine.{Screen, SgineApp, UI}

import java.io.File

trait Example extends SgineApp with DesktopLauncher { self =>
  object screen extends Screen {
    override protected def component: Component = self.component
  }

  override protected def app: SgineApp = this

  protected def component: Component

  override def init(): Unit = {
    UI.title @= getClass.getSimpleName.replace("$", "")
    FPSView.font @= Fonts.OpenSans.Regular.normal
    Audio.resourcesDirectory = Some(new File("src/main/resources"))

    UI.screens @= screen
  }
}