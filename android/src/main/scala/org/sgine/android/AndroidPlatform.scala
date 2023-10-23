//package org.sgine.android
//
//import com.badlogic.gdx.Application
//import com.badlogic.gdx.backends.android.{AndroidApplication, AndroidApplicationConfiguration}
//import org.sgine.{Platform, UI}
//
//trait AndroidPlatform extends AndroidApplication with Platform[AndroidApplicationConfiguration] {
//  override protected def createConfig(): AndroidApplicationConfiguration = new AndroidApplicationConfiguration
//
//  override protected def create(ui: UI, config: AndroidApplicationConfiguration): Application = {
//    initialize(ui.listener, config)
//    this
//  }
//}