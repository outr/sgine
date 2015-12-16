package org.sgine.component.prop

import pl.metastack.metarx.Sub

class FontProperties {
  val family: Sub[String] = Sub[String]("")
  val style: Sub[String] = Sub[String]("")
  val size: Sub[Int] = Sub[Int](0)
}
