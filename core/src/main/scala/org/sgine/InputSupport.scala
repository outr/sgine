package org.sgine

import org.sgine.event._

trait InputSupport {
  val key = new KeyEvents
  val touch = new TouchEvents
  val scrolled = new EventManager[Double]
  val flung = new EventManager[FlingEvent]
  val pan = new PanEvents
  val zoomed = new EventManager[ZoomEvent]
  val pinched = new EventManager[PinchEvent]

  class KeyEvents {
    val down = new EventManager[KeyEvent]
    val up = new EventManager[KeyEvent]
    val typed = new EventManager[KeyEvent]
  }

  class TouchEvents {
    val moved = new EventManager[MouseEvent]
    val down = new EventManager[MouseEvent]
    val up = new EventManager[MouseEvent]
    val dragged = new EventManager[MouseEvent]
    val tapped = new EventManager[MouseEvent]
    val longPressed = new EventManager[MouseEvent]
  }

  class PanEvents {
    val start = new EventManager[PanEvent]
    val stop = new EventManager[MouseEvent]
  }
}