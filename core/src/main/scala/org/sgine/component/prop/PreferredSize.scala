package org.sgine.component.prop

import pl.metastack.metarx.{ReadStateChannel, Var}

class PreferredSize {
  private[sgine] val _width = Var[Double](0.0)
  private[sgine] val _height = Var[Double](0.0)

  def width: ReadStateChannel[Double] = _width
  def height: ReadStateChannel[Double] = _height
}