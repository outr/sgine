package org.sgine.transition.to

import com.badlogic.gdx.Gdx
import org.sgine.Screen
import org.sgine.transition.Transition
import pl.metastack.metarx.Sub

class TransitionTo(val screen: Screen, sub: Sub[Double], to: => Double, from: => Double, time: => Double) extends Transition {
  private lazy val actualTo: Double = to
  private lazy val actualFrom: Double = from
  private lazy val actualTime: Double = time
  lazy val distance = math.abs(to - from)

  override def finished: Boolean = if (actualFrom < actualTo) {
    sub.get >= actualTo
  } else {
    sub.get <= actualTo
  }

  override def invoke(): Unit = {
    val delta = (Gdx.graphics.getDeltaTime.toDouble / actualTime) * distance
    if (actualFrom < actualTo) {
      sub := math.min(sub.get + delta, actualTo)
    } else {
      sub := math.max(sub.get - delta, actualTo)
    }
  }

  override def toString: String = s"TransitionTo(to = $to, from = $from, time = $time)"
}
