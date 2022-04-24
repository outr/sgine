package org.sgine.component

import org.sgine.Screen
import reactify._

trait Component {
  private val _initialized: Var[Boolean] = Var(false)
  protected[sgine] val _parent: Var[Option[Component]] = Var(None)

  /**
   * Visibility flag for this component. Defaults to true.
   */
  val visible: Var[Boolean] = Var(true)

  def initialized: Val[Boolean] = _initialized

  protected def init(): Unit = {}

  protected def verifyInit(): Unit = if (!initialized) {
    _initialized @= true
    init()
  }

  /**
   * Represents the visibility state of this component. Only represents true if all parent components are also visible.
   */
  lazy val isVisible: Val[Boolean] = Val(visible && parent().forall(_.isVisible()))

  /**
   * Parent component for this component.
   */
  val parent: Val[Option[Component]] = _parent

  val screenOption: Val[Option[Screen]] = Val(parent() match {
    case Some(s: Screen) => Some(s)
    case Some(p: Component) => p.screenOption
    case None => None
  })

  screenOption.attach {
    case Some(_) => verifyInit()
    case None => // Not yet fully added to DOM
  }
}