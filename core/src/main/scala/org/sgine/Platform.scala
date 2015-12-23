package org.sgine

import com.badlogic.gdx.Application

/**
  * Platform must be implemented for various graphical implementations of Sgine.
  *
  * @tparam Config the type of configuration instance used
  */
trait Platform[Config] {
  protected val config: Config = createConfig()
  val ui: UI = createUI()

  def main(args: Array[String]): Unit = {
    init(config)
    create(ui, config)
  }

  protected def createUI(): UI
  protected def createConfig(): Config
  def init(config: Config): Unit
  protected def create(ui: UI, config: Config): Application
}