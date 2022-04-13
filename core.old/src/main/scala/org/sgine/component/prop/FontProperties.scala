package org.sgine.component.prop

import reactify._

class FontProperties {
  val family: Var[String] = Var[String]("")
  val style: Var[String] = Var[String]("")
  val size: Var[Int] = Var[Int](0)
}
