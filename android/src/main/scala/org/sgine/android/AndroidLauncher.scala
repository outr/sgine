package org.sgine.android

import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration

trait AndroidLauncher {
  protected def createConfig(): AndroidApplicationConfiguration
}
