package org.sgine.component

import org.sgine.{InputSupport, Screen}

trait Component extends InputSupport {
  def screen: Screen
}
