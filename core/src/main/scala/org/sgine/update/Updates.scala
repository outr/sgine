package org.sgine.update

import reactify.Channel
import reactify.reaction.Reaction

import scala.util.Try

class Updates extends Channel[Double] {
  def every(delay: Double,
            stopIn: Double = Int.MaxValue.toDouble)
           (f: => Unit): Reaction[Double] = {
    var total = 0.0
    var elapsed = 0.0
    var reaction: Reaction[Double] = null
    reaction = attach { delta =>
      total += delta
      elapsed += delta
      if (elapsed >= delay) {
        Try(f)
        elapsed = 0.0
      }
      if (total >= stopIn) {
        reactions -= reaction
      }
    }
    reaction
  }

  def in(delay: Double)(f: => Unit): Unit = every(delay, stopIn = delay)(f)
}