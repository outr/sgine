package org.sgine.event

import com.badlogic.gdx.math.Vector2
import org.sgine.component.Component
import org.sgine.input.Key

trait Event {
  protected var _component: Component = _
  protected var _focused: Option[Component] = None

  final def component: Component = _component
  final def focused: Option[Component] = _focused
}

class KeyEvent private[event]() extends Event {
  protected var _key: Key = _

  final def key: Key = _key
}

object KeyEvent extends KeyEvent {
  private[event] def apply(key: Key,
                           component: Component,
                           focused: Option[Component] = None): KeyEvent = {
    _key = key
    _component = component
    _focused = focused
    this
  }
}

class MouseEvent private[event]() extends Event {
  protected var _button: Int = _
  protected var _screenX: Int = _
  protected var _screenY: Int = _
  protected var _stageX: Double = _
  protected var _stageY: Double = _
  protected var _localX: Double = _
  protected var _localY: Double = _

  final def button: Int = _button
  final def screenX: Int = _screenY
  final def screenY: Int = _screenX
  final def stageX: Double = _stageX
  final def stageY: Double = _stageY
  final def localX: Double = _localX
  final def localY: Double = _localY
}

object MouseEvent extends MouseEvent {
  private[event] def apply(button: Int,
                           screenX: Int,
                           screenY: Int,
                           stageX: Double,
                           stageY: Double,
                           localX: Double,
                           localY: Double,
                           component: Component,
                           focused: Option[Component] = None): MouseEvent = {
    _button = button
    _screenX = screenX
    _screenY = screenY
    _stageX = stageX
    _stageY = stageY
    _localX = localX
    _localY = localY
    _component = component
    _focused = focused
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
                           screenX: Int,
                           screenY: Int,
                           stageX: Double,
                           stageY: Double,
                           localX: Double,
                           localY: Double,
                           component: Component,
                           velocityX: Double,
                           velocityY: Double,
                           focused: Option[Component] = None): FlingEvent = {
    _button = button
    _screenX = screenX
    _screenY = screenY
    _stageX = stageX
    _stageY = stageY
    _localX = localX
    _localY = localY
    _component = component
    _velocityX = velocityX
    _velocityY = velocityY
    _focused = focused
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
                           screenX: Int,
                           screenY: Int,
                           stageX: Double,
                           stageY: Double,
                           localX: Double,
                           localY: Double,
                           component: Component,
                           deltaX: Double,
                           deltaY: Double,
                           focused: Option[Component] = None): PanEvent = {
    _button = button
    _screenX = screenX
    _screenY = screenY
    _stageX = stageX
    _stageY = stageY
    _localX = localX
    _localY = localY
    _component = component
    _deltaX = deltaX
    _deltaY = deltaY
    _focused = focused
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
                           screenX: Int,
                           screenY: Int,
                           stageX: Double,
                           stageY: Double,
                           localX: Double,
                           localY: Double,
                           component: Component,
                           initialFirstPointer: Vector2,
                           initialSecondPointer: Vector2,
                           firstPointer: Vector2,
                           secondPointer: Vector2,
                           focused: Option[Component] = None): PinchEvent = {
    _button = button
    _screenX = screenX
    _screenY = screenY
    _stageX = stageX
    _stageY = stageY
    _localX = localX
    _localY = localY
    _component = component
    _initialFirstPointer = initialFirstPointer
    _initialSecondPointer = initialSecondPointer
    _firstPointer = firstPointer
    _secondPointer = secondPointer
    _focused = focused
    this
  }
}

class ScrollEvent private() extends MouseEvent {
  protected var _amount: Int = _

  final def amount: Int = _amount
}

object ScrollEvent extends ScrollEvent {
  private[event] def apply(button: Int,
                           screenX: Int,
                           screenY: Int,
                           stageX: Double,
                           stageY: Double,
                           localX: Double,
                           localY: Double,
                           component: Component,
                           amount: Int,
                           focused: Option[Component] = None): ScrollEvent = {
    _button = button
    _screenX = screenX
    _screenY = screenY
    _stageX = stageX
    _stageY = stageY
    _localX = localX
    _localY = localY
    _component = component
    _amount = amount
    _focused = focused
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
                           screenX: Int,
                           screenY: Int,
                           stageX: Double,
                           stageY: Double,
                           localX: Double,
                           localY: Double,
                           component: Component,
                           originalDistance: Double,
                           currentDistance: Double,
                           focused: Option[Component] = None): ZoomEvent = {
    _button = button
    _screenX = screenX
    _screenY = screenY
    _stageX = stageX
    _stageY = stageY
    _localX = localX
    _localY = localY
    _component = component
    _originalDistance = originalDistance
    _currentDistance = currentDistance
    _focused = focused
    this
  }
}