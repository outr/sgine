package org.sgine.transition.to

import com.badlogic.gdx.Gdx
import org.sgine.Screen
import org.sgine.transition.Transition
import pl.metastack.metarx.Sub

class TransitionTo(val screen: Screen, sub: Sub[Double], to: Double, from: Double, time: Double) extends Transition {
  val distance = math.abs(to - from)

  override protected def continue: Boolean = if (from < to) {
    sub.get >= to
  } else {
    sub.get <= to
  }

  override protected def invoke(): Unit = {
    val delta = (Gdx.graphics.getDeltaTime.toDouble / time) * distance
    if (from < to) {
      sub := math.min(sub.get + delta, to)
    } else {
      sub := math.max(sub.get - delta, to)
    }
  }
}
