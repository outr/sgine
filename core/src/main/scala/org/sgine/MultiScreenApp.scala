package org.sgine

trait MultiScreenApp {
  protected def init(): Unit

  def main(args: Array[String]): Unit = UI.run {
    init()
  }
}
