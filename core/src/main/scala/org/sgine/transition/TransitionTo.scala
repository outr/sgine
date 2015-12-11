package org.sgine.transition

import com.badlogic.gdx.Gdx
import org.sgine.transition.easing.Easing

case class TransitionTo(setter: (Double) => Unit,
                        getter: () => Double,
                        _to: () => Double,
                        _from: () => Option[Double] = () => None,
                        _time: () => Option[Double] = () => None,
                        _step: () => Option[Double] = () => None,
                        _easing: () => Option[Easing] = () => None) extends Transition {
  private var elapsed: Double = 0.0
  private var origin: Double = 0.0
  private var multiplier: Double = 0.0
  private var destination: Double = 0.0
  private var duration: Double = 0.0
  private var easing: Option[Easing] = None

  override def init(): Unit = {
    elapsed = 0.0
    destination = _to()
    easing = _easing()
    origin = _from() match {
      case Some(f) => {
        setter(f)
        f
      }
      case None => getter()
    }
    val distance = math.abs(destination - origin)
    multiplier = _time() match {
      case Some(t) => {
        duration = t
        distance / t
      }
      case None => _step() match {
        case Some(s) => {
          if (easing.nonEmpty) throw new RuntimeException(s"Easings do not work with step sizes, only with time.")
          s
        }
        case None => throw new RuntimeException(s"Time or Step must be defined in order to use a TransitionTo.")
      }
    }
  }

  override def finished: Boolean = if (easing.isDefined) elapsed >= duration else getter() == destination

  override def invoke(): Unit = {
    val delta = Gdx.graphics.getDeltaTime.toDouble
    elapsed += delta
    val change = multiplier * delta
    val current = getter()
    easing match {
      case Some(e) => if (elapsed < duration) {
        setter(e(elapsed = elapsed, start = origin, target = destination, duration = duration))
      } else {
        setter(destination)
      }
      case None => {
        if (destination > current) {
          setter(math.min(getter() + change, destination))
        } else {
          setter(math.max(getter() - change, destination))
        }
      }
    }
  }

  def from(from: => Double): TransitionTo = copy(_from = () => Some(from))
  def in(in: => Double): TransitionTo = copy(_time = () => Some(in))
  def by(step: => Double): TransitionTo = copy(_step = () => Some(step))
  def easing(easing: => Easing): TransitionTo = copy(_easing = () => Some(easing))

  override def toString: String = s"TransitionTo(to = ${_to()}, from = ${_from()}, time = ${_time()})"
}
