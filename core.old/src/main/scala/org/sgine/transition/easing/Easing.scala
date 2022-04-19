package org.sgine.transition.easing

trait Easing {
  def apply(elapsed: Double, start: Double, target: Double, duration: Double): Double
}

class EasingFunction(val f: (Double, Double, Double, Double) => Double) extends Easing {
  def apply(elapsed: Double, start: Double, target: Double, duration: Double): Double = {
    f(elapsed, start, target - start, duration)
  }
}

object Easing {
  lazy val BackIn = new EasingFunction(Back.easeIn)
  lazy val BackOut = new EasingFunction(Back.easeOut)
  lazy val BackInOut = new EasingFunction(Back.easeInOut)
  lazy val BounceIn = new EasingFunction(Bounce.easeIn)
  lazy val BounceOut = new EasingFunction(Bounce.easeOut)
  lazy val BounceInOut = new EasingFunction(Bounce.easeInOut)
  lazy val CircularIn = new EasingFunction(Circular.easeIn)
  lazy val CircularOut = new EasingFunction(Circular.easeOut)
  lazy val CircularInOut = new EasingFunction(Circular.easeInOut)
  lazy val CubicIn = new EasingFunction(Cubic.easeIn)
  lazy val CubicOut = new EasingFunction(Cubic.easeOut)
  lazy val CubicInOut = new EasingFunction(Cubic.easeInOut)
  lazy val ElasticIn = new EasingFunction(Elastic.easeIn)
  lazy val ElasticOut = new EasingFunction(Elastic.easeOut)
  lazy val ElasticInOut = new EasingFunction(Elastic.easeInOut)
  lazy val ExponentialIn = new EasingFunction(Exponential.easeIn)
  lazy val ExponentialOut = new EasingFunction(Exponential.easeOut)
  lazy val ExponentialInOut = new EasingFunction(Exponential.easeInOut)
  lazy val LinearIn = new EasingFunction(Linear.easeIn)
  lazy val LinearOut = new EasingFunction(Linear.easeOut)
  lazy val LinearInOut = new EasingFunction(Linear.easeInOut)
  lazy val QuadraticIn = new EasingFunction(Quadratic.easeIn)
  lazy val QuadraticOut = new EasingFunction(Quadratic.easeOut)
  lazy val QuadraticInOut = new EasingFunction(Quadratic.easeInOut)
  lazy val QuarticIn = new EasingFunction(Quartic.easeIn)
  lazy val QuarticOut = new EasingFunction(Quartic.easeOut)
  lazy val QuarticInOut = new EasingFunction(Quartic.easeInOut)
  lazy val QuinticIn = new EasingFunction(Quintic.easeIn)
  lazy val QuinticOut = new EasingFunction(Quintic.easeOut)
  lazy val QuinticInOut = new EasingFunction(Quintic.easeInOut)
  lazy val SineIn = new EasingFunction(Sine.easeIn)
  lazy val SineOut = new EasingFunction(Sine.easeOut)
  lazy val SineInOut = new EasingFunction(Sine.easeInOut)
}