package org.sgine.component

import org.sgine.Screen
import reactify._

trait Component { c =>
  private val _initialized: Var[Boolean] = Var(false)
  protected[sgine] val _parent: Var[Option[Component]] = Var(None)

  /**
   * Visibility flag for this component. Defaults to true.
   */
  val visible: Var[Boolean] = Var(true)

  val includeInLayout: Var[Boolean] = Var(true)

  def initialized: Val[Boolean] = _initialized

  protected def init(): Unit = {}

  def verifyInit(): Unit = if (!initialized) {
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

  protected def screenX: Double = parent().map(_.screenX).getOrElse(0.0)
  protected def screenY: Double = parent().map(_.screenY).getOrElse(0.0)

  /**
   * Access to global coordinates within the screen
   */
  object global {
    def x: Double = screenX
    def y: Double = screenY

    protected def w: Double = c match {
      case d: DimensionedSupport => d.width()
      case _ => 0.0
    }
    protected def h: Double = c match {
      case d: DimensionedSupport => d.height()
      case _ => 0.0
    }

    def left: Double = x
    def center: Double = x + (w / 2.0)
    def right: Double = x + w

    def top: Double = y
    def middle: Double = y + (h / 2.0)
    def bottom: Double = y + h

    /**
     * Converts a global x value to local coordinates
     */
    def localizeX(x: Double): Double = x - screenX

    /**
     * Converts a global y value to local coordinates
     */
    def localizeY(y: Double): Double = y - screenY
  }

  val screenOption: Val[Option[Screen]] = Val(parent() match {
    case Some(s: Screen) => Some(s)
    case Some(p: Component) => p.screenOption
    case None => this match {
      case s: Screen => Some(s)
      case _ => None
    }
  })

  screenOption.attach {
    case Some(_) => verifyInit()
    case None => // Not yet fully added to DOM
  }

  override def toString: String = getClass.getName
}

object Component {
  def flatChildren(component: Component): Vector[Component] = component match {
    case c if !c.visible() => Vector.empty
    case container: TypedContainer[_] => container +: container.children.flatMap { child =>
      flatChildren(child)
    }
    case _ => Vector(component)
  }
}