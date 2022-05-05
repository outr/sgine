package org.sgine

trait SingleScreenApp extends Screen {
  protected def screen: Screen = this

  def main(args: Array[String]): Unit = UI.run {
    UI.screen @= this
  }
}