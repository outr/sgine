package org.sgine.event.key

/**
 * KeyState is an enum that represents the current state of a Key.
 */
sealed trait KeyState

object KeyState {
  case object Up extends KeyState
  case object Down extends KeyState
}