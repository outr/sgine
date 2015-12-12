package org.sgine.event

import com.badlogic.gdx.math.Vector2
import org.sgine.component.Component
import org.sgine.input.Key

trait Event {
  protected var _focused: Option[Component] = None
  protected var _atCursor: Option[Component] = None

  final def focused: Option[Component] = _focused
  final def atCursor: Option[Component] = _atCursor
}

class KeyEvent private() extends Event {
  protected var _key: Key = _

  final def key: Key = _key
}

object KeyEvent extends KeyEvent {
  private[event] def apply(key: Key,
                           focused: Option[Component] = None,
                           atCursor: Option[Component] = None): KeyEvent = {
    _key = key
    _focused = focused
    _atCursor = atCursor
    this
  }
}

class MouseEvent private() extends Event {
  protected var _button: Int = _
  protected var _x: Double = _
  protected var _y: Double = _

  final def button: Int = _button
  final def x: Double = _x
  final def y: Double = _y
}

object MouseEvent extends MouseEvent {
  private[event] def apply(button: Int,
                           x: Double,
                           y: Double,
                           focused: Option[Component] = None,
                           atCursor: Option[Component] = None): MouseEvent = {
    _button = button
    _x = x
    _y = y
    _focused = focused
    _atCursor = atCursor
    this
  }
}

class FlingEvent private() extends MouseEvent {
  protected var _velocityX: Double = _
  protected var _velocityY: Double = _

  final def velocityX: Double = _velocityX
  final def velocityY: Double = _velocityY
}

object FlingEvent extends FlingEvent {
  private[event] def apply(button: Int,
                           x: Double,
                           y: Double,
                           velocityX: Double,
                           velocityY: Double,
                           focused: Option[Component] = None,
                           atCursor: Option[Component] = None): FlingEvent = {
    _button = button
    _x = x
    _y = y
    _velocityX = velocityX
    _velocityY = velocityY
    _focused = focused
    _atCursor = atCursor
    this
  }
}

class PanEvent private() extends MouseEvent {
  protected var _deltaX: Double = _
  protected var _deltaY: Double = _

  final def deltaX: Double = _deltaX
  final def deltaY: Double = _deltaY
}

object PanEvent extends PanEvent {
  private[event] def apply(button: Int,
                           x: Double,
                           y: Double,
                           deltaX: Double,
                           deltaY: Double,
                           focused: Option[Component] = None,
                           atCursor: Option[Component] = None): PanEvent = {
    _button = button
    _x = x
    _y = y
    _deltaX = deltaX
    _deltaY = deltaY
    _focused = focused
    _atCursor = atCursor
    this
  }
}

class PinchEvent private() extends MouseEvent {
  protected var _initialFirstPointer: Vector2 = _
  protected var _initialSecondPointer: Vector2 = _
  protected var _firstPointer: Vector2 = _
  protected var _secondPointer: Vector2 = _

  final def initialFirstPointer: Vector2 = _initialFirstPointer
  final def initialSecondPointer: Vector2 = _initialSecondPointer
  final def firstPointer: Vector2 = _firstPointer
  final def secondPointer: Vector2 = _secondPointer
}

object PinchEvent extends PinchEvent {
  private[event] def apply(button: Int,
                           x: Double,
                           y: Double,
                           initialFirstPointer: Vector2,
                           initialSecondPointer: Vector2,
                           firstPointer: Vector2,
                           secondPointer: Vector2,
                           focused: Option[Component] = None,
                           atCursor: Option[Component] = None): PinchEvent = {
    _button = button
    _x = x
    _y = y
    _initialFirstPointer = initialFirstPointer
    _initialSecondPointer = initialSecondPointer
    _firstPointer = firstPointer
    _secondPointer = secondPointer
    _focused = focused
    _atCursor = atCursor
    this
  }
}

class ScrollEvent private() extends MouseEvent {
  protected var _amount: Int = _

  final def amount: Int = _amount
}

object ScrollEvent extends ScrollEvent {
  private[event] def apply(button: Int,
                           x: Double,
                           y: Double,
                           amount: Int,
                           focused: Option[Component] = None,
                           atCursor: Option[Component] = None): ScrollEvent = {
    _button = button
    _x = x
    _y = y
    _amount = amount
    _focused = focused
    _atCursor = atCursor
    this
  }
}

class ZoomEvent private() extends MouseEvent {
  protected var _originalDistance: Double = _
  protected var _currentDistance: Double = _

  def originalDistance: Double = _originalDistance
  def currentDistance: Double = _currentDistance
}

object ZoomEvent extends ZoomEvent {
  private[event] def apply(button: Int,
                           x: Double,
                           y: Double,
                           originalDistance: Double,
                           currentDistance: Double,
                           focused: Option[Component] = None,
                           atCursor: Option[Component] = None): ZoomEvent = {
    _button = button
    _x = x
    _y = y
    _originalDistance = originalDistance
    _currentDistance = currentDistance
    _focused = focused
    _atCursor = atCursor
    this
  }
}