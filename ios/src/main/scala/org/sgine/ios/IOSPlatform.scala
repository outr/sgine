package org.sgine.ios

import com.badlogic.gdx.Application
import com.badlogic.gdx.backends.iosrobovm.{IOSApplication, IOSApplicationConfiguration}
import org.sgine.{Platform, UI}

trait IOSPlatform extends IOSApplication.Delegate with Platform[IOSApplicationConfiguration] {
  override protected def createConfig(): IOSApplicationConfiguration = new IOSApplicationConfiguration

  override protected def create(ui: UI, config: IOSApplicationConfiguration): Application = {
    new IOSApplication(ui.listener, config)
  }
}