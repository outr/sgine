package org.sgine

trait SgineApp {
  protected def init(): Unit

  def main(args: Array[String]): Unit = UI.run {
    UI.title @= getClass.getSimpleName.replace("$", "")
    init()
  }
}
