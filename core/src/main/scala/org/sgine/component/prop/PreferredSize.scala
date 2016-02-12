package org.sgine.component.prop

import pl.metastack.metarx.{ReadStateChannel, Var}

class PreferredSize {
  private val _width = Var[Double](0.0)
  private val _height = Var[Double](0.0)

  def width: ReadStateChannel[Double] = _width
  def height: ReadStateChannel[Double] = _height

  def width(value: Double): Unit = _width := value
  def height(value: Double): Unit = _height := value
}