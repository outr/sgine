package org.sgine

import org.sgine.render.{ClearScreenSupport, SingleScreenSupport}

/**
  * Provides a simple combination of UI for a single screen
  */
trait BasicUI extends UI with ClearScreenSupport with SingleScreenSupport