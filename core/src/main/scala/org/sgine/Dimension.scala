package org.sgine

import reactify._

class Dimension extends Var[Double] {
  val preferred: Var[Double] = Var(0.0)
  val min: Var[Double] = Var(0.0)
  val max: Var[Double] = Var(Double.MaxValue)

  set(math.min(max, math.max(min, preferred)))
}