package org.sgine.component.prop

import pl.metastack.metarx.{ReadStateChannel, Var}

class PreferredSize {
  private val _width = Var[Double](0.0)
  private val _height = Var[Double](0.0)

  def width: ReadStateChannel[Double] = _width
  def height: ReadStateChannel[Double] = _height
}

object PreferredSize {
  def update(p: PreferredSize, width: Double, height: Double): Unit = {
    p._width := width
    p._height := height
  }
}