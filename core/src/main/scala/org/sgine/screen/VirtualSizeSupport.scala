package org.sgine.screen

import org.sgine._
import pl.metastack.metarx.ReadChannel

trait VirtualSizeSupport extends Screen {
  def virtualWidth: Double
  def virtualHeight: Double
  // TODO: support maintaining aspect ratio (clip, stretch, bars)

  implicit class DoubleVirtualPixels(d: Double) {
    def vw: ReadChannel[Double] = d * (ui.width / virtualWidth)
    def vh: ReadChannel[Double] = d * (ui.height / virtualHeight)
  }
}