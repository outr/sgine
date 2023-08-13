package org.sgine.component

import org.sgine.util.MathUtils
import reactify.Val

/**
 * Targeting mix-in provides convenience functionality to target nearby
 */
trait Targeting[Target <: Component] extends Component {
  lazy val nearestTargets: Val[List[Target]] = Val {
    targets.sortBy(calculateDistance)
  }
  lazy val currentTarget: Val[Option[Target]] = Val {
    nearestTargets()
      .iterator
      .takeWhile(c => calculateDistance(c) <= maxDistance && shouldTarget(c))
      .nextOption()
  }

  protected def maxDistance: Double

  protected def shouldTarget(c: Target): Boolean = true

  protected def calculateDistance(c: Target): Double = MathUtils.distanceFromCenter(this, c)

  protected def targets: List[Target]
}
