package org.sgine

trait App {
  protected def init(): Unit

  def main(args: Array[String]): Unit = UI.run {
    init()
  }
}
