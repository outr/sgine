package org.sgine

import io.youi.Color
import org.sgine.component.prop.FontProperties
import reactify._

/**
  * Theme provides defaults that are used by visual elements. This is instantiated in the UI instance and can be
  * modified at any time to reflect theme changes and will apply to existing and new elements unless they have declared
  * an override.
  */
class Theme {
  val font: FontProperties = new FontProperties
  val blinkTime: Var[Double] = Var[Double](0.32)
  val selectionColor: Var[Color] = Var[Color](Color.LightSkyBlue)
  val placeholderColor: Var[Color] = Var[Color](Color.DimGray)
  val ellipsis: Var[Option[String]] = Var[Option[String]](None)

  font.family := "OpenSans"
  font.style := "Semibold"
  font.size := 18
}