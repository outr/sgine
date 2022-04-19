package org.sgine

trait SingleScreenApp extends Screen {
  def init(): Unit = {}

  def main(args: Array[String]): Unit = UI.run {
    init()
    UI.drawFPS @= true
    UI.screen @= this
  }
}