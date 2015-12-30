package org.sgine.component.prop

import pl.metastack.metarx._

class DependentVar(sub: Sub[Double], apply: ReadChannel[Double] => ReadChannel[Double], unapply: => Double) {
  def :=(channel: ReadChannel[Double]) = sub := apply(channel)
  def :=(value: Double) = sub := apply(Var(value))
  def get = unapply
}