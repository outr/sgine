package org.sgine.transition

import com.badlogic.gdx.Gdx
import org.sgine.Screen
import pl.metastack.metarx.Sub

case class TransitionTo(screen: Screen,
                   sub: Sub[Double],
                   _to: () => Double,
                   _from: () => Option[Double] = () => None,
                   _time: () => Option[Double] = () => None,
                   _step: () => Option[Double] = () => None) extends Transition {
  private var multiplier: Double = 0.0
  private var destination: Double = 0.0

  override def init(): Unit = {
    destination = _to()
    val origin = _from() match {
      case Some(f) => {
        sub := f
        f
      }
      case None => sub.get
    }
    val distance = math.abs(destination - origin)
    multiplier = _time() match {
      case Some(t) => distance / t
      case None => _step() match {
        case Some(s) => s
        case None => throw new RuntimeException(s"Time or Step must be defined in order to use a TransitionTo.")
      }
    }
  }

  override def finished: Boolean = sub.get == destination

  override def invoke(): Unit = {
    val delta = multiplier * Gdx.graphics.getDeltaTime.toDouble
    val current = sub.get
    if (destination > current) {
      sub := math.min(sub.get + delta, destination)
    } else {
      sub := math.max(sub.get - delta, destination)
    }
  }

  def from(from: => Double): TransitionTo = copy(_from = () => Some(from))
  def in(in: => Double): TransitionTo = copy(_time = () => Some(in))
  def by(step: => Double): TransitionTo = copy(_step = () => Some(step))

  override def toString: String = s"TransitionTo(to = ${_to()}, from = ${_from()}, time = ${_time()}"
}
