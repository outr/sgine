package org.sgine.component.prop

import org.sgine.component.DimensionedComponent
import pl.metastack.metarx.{ReadChannel, Sub}

class SizeProperties(component: DimensionedComponent) {
  val width: Sub[Double] = Sub(0.0)
  val height: Sub[Double] = Sub(0.0)

  def maintainAspectRatio(width: ReadChannel[Double] = null, height: ReadChannel[Double] = null): Unit = Option(width) match {
    case Some(w) => if (Option(height).isDefined) {
      throw new RuntimeException(s"Cannot maintain aspect ratio with both values defined.")
    } else {
      val aspect = this.width.get / this.height.get
      this.width := w
      this.height := this.width / aspect
    }
    case None => Option(height) match {
      case Some(h) => {
        val aspect = this.height.get / this.width.get
        this.height := h
        this.width := this.height / aspect
      }
      case None => throw new RuntimeException(s"Cannot update dimensions maintaining aspect ratio unless width or height is defined.")
    }
  }
}