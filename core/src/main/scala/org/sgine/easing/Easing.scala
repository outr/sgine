package org.sgine.easing

import scala.util.Random

trait Easing {
  /**
    * Takes a progress (0.0 to 1.0) and applies the eamath.sing algorithm to get the return value.
    *
    * @param progress values 0.0 (start) to 1.0 (end) representing the percent complete
    * @return eased value
    */
  def calculate(progress: Double): Double
}

object Easing {
  def backIn(overshoot: Double = 1.70158): Easing = BackIn(overshoot)
  def backOut(overshoot: Double = 1.70158): Easing = BackOut(overshoot)
  def backInOut(overshoot: Double = 1.70158): Easing = BackInOut(overshoot)
  def linear: Easing = Linear
  def bounceIn: Easing = wrap(Bounce.easeIn)
  def bounceOut: Easing = wrap(Bounce.easeOut)
  def bounceInOut: Easing = wrap(Bounce.easeInOut)
  def circularIn: Easing = wrap(Circular.easeIn)
  def circularOut: Easing = wrap(Circular.easeOut)
  def circularInOut: Easing = wrap(Circular.easeInOut)
  def cubicIn: Easing = wrap(Cubic.easeIn)
  def cubicOut: Easing = wrap(Cubic.easeOut)
  def cubicInOut: Easing = wrap(Cubic.easeInOut)
  def elasticIn: Easing = wrap(Elastic.easeIn)
  def elasticOut: Easing = wrap(Elastic.easeOut)
  def elasticInOut: Easing = wrap(Elastic.easeInOut)
  def exponentialIn: Easing = wrap(Exponential.easeIn)
  def exponentialOut: Easing = wrap(Exponential.easeOut)
  def exponentialInOut: Easing = wrap(Exponential.easeInOut)
  def quadraticIn: Easing = wrap(Quadratic.easeIn)
  def quadraticOut: Easing = wrap(Quadratic.easeOut)
  def quadraticInOut: Easing = wrap(Quadratic.easeInOut)
  def quarticIn: Easing = wrap(Quartic.easeIn)
  def quarticOut: Easing = wrap(Quartic.easeOut)
  def quarticInOut: Easing = wrap(Quartic.easeInOut)
  def quinticIn: Easing = wrap(Quintic.easeIn)
  def quinticOut: Easing = wrap(Quintic.easeOut)
  def quinticInOut: Easing = wrap(Quintic.easeInOut)
  def sineIn: Easing = wrap(Sine.easeIn)
  def sineOut: Easing = wrap(Sine.easeOut)
  def sineInOut: Easing = wrap(Sine.easeInOut)

  lazy val map: Map[String, Easing] = Map(
    "BackIn" -> Easing.backIn(),
    "BackOut" -> Easing.backOut(),
    "BackInOut" -> Easing.backInOut(),
    "Linear" -> Easing.linear,
    "BounceIn" -> Easing.bounceIn,
    "BounceOut" -> Easing.bounceOut,
    "BounceInOut" -> Easing.bounceInOut,
    "CircularIn" -> Easing.circularIn,
    "CircularOut" -> Easing.circularOut,
    "CircularInOut" -> Easing.circularInOut,
    "CubicIn" -> Easing.cubicIn,
    "CubicOut" -> Easing.cubicOut,
    "CubicInOut" -> Easing.cubicInOut,
    "ElasticIn" -> Easing.elasticIn,
    "ElasticOut" -> Easing.elasticOut,
    "ElasticInOut" -> Easing.elasticInOut,
    "ExponentialIn" -> Easing.exponentialIn,
    "ExponentialOut" -> Easing.exponentialOut,
    "ExponentialInOut" -> Easing.exponentialInOut,
    "QuadraticIn" -> Easing.quadraticIn,
    "QuadraticOut" -> Easing.quadraticOut,
    "QuadraticInOut" -> Easing.quadraticInOut,
    "QuarticIn" -> Easing.quarticIn,
    "QuarticOut" -> Easing.quarticOut,
    "QuarticInOut" -> Easing.quarticInOut,
    "QuinticIn" -> Easing.quinticIn,
    "QuinticOut" -> Easing.quinticOut,
    "QuinticInOut" -> Easing.quinticInOut,
    "SineIn" -> Easing.sineIn,
    "SineOut" -> Easing.sineOut,
    "SineInOut" -> Easing.sineInOut
  )
  lazy val all: Vector[Easing] = map.values.toVector

  def random: Easing = all(Random.nextInt(all.size))

  private def wrap(f: (Double, Double, Double, Double) => Double): Easing = new Easing {
    override def calculate(progress: Double): Double = f(progress, 0.0, 1.0, 1.0)
  }
}