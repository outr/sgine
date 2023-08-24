package org.sgine.component

import org.sgine.util.MathUtils
import reactify.Val

/**
 * Targeting mix-in provides convenience functionality to target nearby
 */
trait Targeting[Target <: Component] extends Component {
  lazy val currentTargets: Val[Seq[Target]] = Val {
    targets
      .filter(c => shouldTarget(c, calculateDistance(c)))
      .take(maxTargets)
  }

  protected def maxDistance: Double

  protected def maxTargets: Int

  protected def shouldTarget(c: Target, distance: Double): Boolean = distance <= maxDistance

  protected def calculateDistance(c: Target): Double = MathUtils.distanceFromCenter(this, c)

  protected def targets: Seq[Target]
}
