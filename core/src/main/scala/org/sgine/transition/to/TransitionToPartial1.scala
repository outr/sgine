package org.sgine.transition.to

import org.sgine.Screen
import pl.metastack.metarx.Sub

class TransitionToPartial1(screen: Screen, sub: Sub[Double], to: => Double) {
  def from(from: => Double): TransitionToPartial2 = new TransitionToPartial2(screen, sub, to, from)
}