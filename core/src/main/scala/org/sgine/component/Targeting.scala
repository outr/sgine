package org.sgine.component

import org.sgine.util.MathUtils
import reactify.Val

/**
 * Targeting mix-in provides convenience functionality to target nearby
 */
trait Targeting[Target <: Component] extends Component {
  lazy val currentTarget: Val[Option[Target]] = Val {
    targets.find(c => shouldTarget(c, calculateDistance(c)))
  }

  protected def maxDistance: Double

  protected def shouldTarget(c: Target, distance: Double): Boolean = distance <= maxDistance

  protected def calculateDistance(c: Target): Double = MathUtils.distanceFromCenter(this, c)

  protected def targets: Seq[Target]
}
