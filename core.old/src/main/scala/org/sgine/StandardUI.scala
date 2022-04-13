package org.sgine

import org.sgine.render.{ClearScreenSupport, MultiScreenSupport}

/**
  * StandardUI is the standard use-case for a UI providing for multi-screen display.
  */
trait StandardUI extends UI with ClearScreenSupport with MultiScreenSupport