package org.sgine

import org.sgine.event._
import pl.metastack.metarx.Channel

trait InputSupport {
  lazy val key: KeyEvents = new KeyEvents
  lazy val mouse: MouseEvents = new MouseEvents
  lazy val scrolled: Channel[ScrollEvent] = Channel[ScrollEvent]
  lazy val flung: Channel[FlingEvent] = Channel[FlingEvent]
  lazy val pan: PanEvents = new PanEvents
  lazy val zoomed: Channel[ZoomEvent] = Channel[ZoomEvent]
  lazy val pinched: Channel[PinchEvent] = Channel[PinchEvent]

  class KeyEvents {
    lazy val down: Channel[KeyEvent] = Channel[KeyEvent]
    lazy val up: Channel[KeyEvent] = Channel[KeyEvent]
    lazy val typed: Channel[KeyEvent] = Channel[KeyEvent]
  }

  class MouseEvents {
    lazy val moved: Channel[MouseEvent] = Channel[MouseEvent]
    lazy val down: Channel[MouseEvent] = Channel[MouseEvent]
    lazy val up: Channel[MouseEvent] = Channel[MouseEvent]
    lazy val dragged: Channel[MouseEvent] = Channel[MouseEvent]
    lazy val tapped: Channel[MouseEvent] = Channel[MouseEvent]
    lazy val longPressed: Channel[MouseEvent] = Channel[MouseEvent]
  }

  class PanEvents {
    lazy val start: Channel[PanEvent] = Channel[PanEvent]
    lazy val stop: Channel[MouseEvent] = Channel[MouseEvent]
  }
}