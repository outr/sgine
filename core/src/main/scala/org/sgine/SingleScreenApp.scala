package org.sgine

trait SingleScreenApp extends Screen {
  protected def screen: Screen = this

  def main(args: Array[String]): Unit = UI.run {
    verifyInit()
    UI.drawFPS @= true
    UI.screen @= this
  }
}