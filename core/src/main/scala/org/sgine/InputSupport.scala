package org.sgine

import org.sgine.event._
import pl.metastack.metarx.Channel

trait InputSupport {
  val key: KeyEvents = new KeyEvents
  val touch: TouchEvents = new TouchEvents
  val scrolled: Channel[ScrollEvent] = Channel[ScrollEvent]
  val flung: Channel[FlingEvent] = Channel[FlingEvent]
  val pan: PanEvents = new PanEvents
  val zoomed: Channel[ZoomEvent] = Channel[ZoomEvent]
  val pinched: Channel[PinchEvent] = Channel[PinchEvent]

  class KeyEvents {
    val down: Channel[KeyEvent] = Channel[KeyEvent]
    val up: Channel[KeyEvent] = Channel[KeyEvent]
    val typed: Channel[KeyEvent] = Channel[KeyEvent]
  }

  class TouchEvents {
    val moved: Channel[MouseEvent] = Channel[MouseEvent]
    val down: Channel[MouseEvent] = Channel[MouseEvent]
    val up: Channel[MouseEvent] = Channel[MouseEvent]
    val dragged: Channel[MouseEvent] = Channel[MouseEvent]
    val tapped: Channel[MouseEvent] = Channel[MouseEvent]
    val longPressed: Channel[MouseEvent] = Channel[MouseEvent]
  }

  class PanEvents {
    val start: Channel[PanEvent] = Channel[PanEvent]
    val stop: Channel[MouseEvent] = Channel[MouseEvent]
  }
}