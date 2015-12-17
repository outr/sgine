package org.sgine

import org.sgine.component.prop.FontProperties
import pl.metastack.metarx.Sub

class Theme {
  val font: FontProperties = new FontProperties
  val blinkTime: Sub[Double] = Sub[Double](0.32)
  val selectionColor: Sub[Color] = Sub[Color](Color.LightSkyBlue)
  val placeholderColor: Sub[Color] = Sub[Color](Color.DimGray)
  val ellipsis: Sub[Option[String]] = Sub[Option[String]](None)

  font.family := "OpenSans"
  font.style := "Semibold"
  font.size := 18
}