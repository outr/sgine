package org.sgine

import org.sgine.component.prop.FontProperties
import pl.metastack.metarx.Sub

/**
  * Theme provides defaults that are used by visual elements. This is instantiated in the UI instance and can be
  * modified at any time to reflect theme changes and will apply to existing and new elements unless they have declared
  * an override.
  */
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