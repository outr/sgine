package org.sgine.dialog

import org.sgine.Overlay
import org.sgine.component.{DimensionedSupport, MutableContainer}

trait Dialog extends DimensionedSupport {
  visible @= false
  Dialog.children += this
}

object Dialog extends MutableContainer[Dialog] {
  Overlay.children += this
}