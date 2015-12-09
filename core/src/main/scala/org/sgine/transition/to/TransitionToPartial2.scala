package org.sgine.transition.to

import org.sgine.Screen
import pl.metastack.metarx.Sub

class TransitionToPartial2(screen: Screen, sub: Sub[Double], to: => Double, from: => Double) {
  def in(time: => Double): TransitionTo = new TransitionTo(screen, sub, to, from, time)
}
