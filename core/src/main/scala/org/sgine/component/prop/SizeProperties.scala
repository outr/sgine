package org.sgine.component.prop

import org.sgine.component.DimensionedComponent
import pl.metastack.metarx.{ReadChannel, Sub}

class SizeProperties(component: DimensionedComponent) {
  val width: Sub[Double] = Sub(0.0)
  val height: Sub[Double] = Sub(0.0)

  lazy val center: ReadChannel[Double] = width / 2.0
  lazy val middle: ReadChannel[Double] = height / 2.0

  def maintainAspectRatio(width: ReadChannel[Double] = null, height: ReadChannel[Double] = null): Unit = component.screen.render.once {
    Option(width) match {
      case Some(w) => if (Option(height).isDefined) {
        throw new RuntimeException(s"Cannot maintain aspect ratio with both values defined.")
      } else {
        this.width := w
        this.height := this.width / (component.preferred.width / component.preferred.height)
      }
      case None => Option(height) match {
        case Some(h) => {
          this.height := h
          this.width := this.height / (component.preferred.height / component.preferred.width)
        }
        case None => throw new RuntimeException(s"Cannot update dimensions maintaining aspect ratio unless width or height is defined.")
      }
    }
  }
}