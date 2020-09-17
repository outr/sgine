package org.sgine.transition

import org.sgine._
import org.sgine.render.MultiScreenSupport
import org.sgine.transition.easing.Easing
import reactify._

trait Transition {
  def finished: Boolean
  def init(): Unit
  def invoke(): Unit

  def start(context: Renderable) = context.render.once {
    var first = true
    context.render.until(finished) {
      if (first) {
        init()
        first = false
      }
      invoke()
    }
  }

  def andThen(next: Transition): Sequence = new Sequence(List(this, next))
  def and(other: Transition): Parallel = new Parallel(Set(this, other))
}

object Transition {
  object screen {
    private def mui = ui.asInstanceOf[MultiScreenSupport]

    object fade {
      def cross(oldScreen: Screen,
                newScreen: Screen,
                time: Double,
                easing: Easing = Easing.LinearIn,
                add: Boolean = true,
                remove: Boolean = true): Transition = {
        val fadeOut = out(oldScreen, time, easing, remove)
        val fadeIn = in(newScreen, time, easing, add)
        fadeOut and fadeIn
      }

      def out(screen: Screen, time: Double, easing: Easing = Easing.LinearIn, remove: Boolean = true): Transition = {
        apply(screen, 0.0, time, easing) andThen function {
          mui.activeScreens.remove(screen)
        }
      }
      def in(screen: Screen, time: Double, easing: Easing = Easing.LinearIn, add: Boolean = true): Transition = {
        function {
          screen.color.alpha := 0.0
          mui.activeScreens.add(screen)
        } andThen apply(screen, 1.0, time, easing)
      }

      def apply(screen: Screen, value: Double, time: Double, easing: Easing): Transition = {
        screen.color.alpha transitionTo value in time easing easing
      }
    }
    object push {
      def up(oldScreen: Screen, newScreen: Screen, time: Double, easing: Easing = Easing.LinearIn): Transition = {
        apply(oldScreen, newScreen, time, easing, newScreen.position.y, oldScreen.position.y, ui.height.get)
      }
      def down(oldScreen: Screen, newScreen: Screen, time: Double, easing: Easing = Easing.LinearIn): Transition = {
        apply(oldScreen, newScreen, time, easing, newScreen.position.y, oldScreen.position.y, -ui.height.get)
      }
      def left(oldScreen: Screen, newScreen: Screen, time: Double, easing: Easing = Easing.LinearIn): Transition = {
        apply(oldScreen, newScreen, time, easing, newScreen.position.x, oldScreen.position.x, -ui.width.get)
      }
      def right(oldScreen: Screen, newScreen: Screen, time: Double, easing: Easing = Easing.LinearIn): Transition = {
        apply(oldScreen, newScreen, time, easing, newScreen.position.x, oldScreen.position.x, ui.width.get)
      }

      def apply(oldScreen: Screen,
                newScreen: Screen,
                time: Double,
                easing: Easing,
                inVar: Var[Double],
                outVar: Var[Double],
                outDestination: => Double): Transition = {
        val pushOut = outVar transitionTo outDestination in time easing easing
        val pushIn = inVar transitionTo 0.0 in time easing easing
        function {
          inVar := -outDestination
          mui.activeScreens.add(newScreen)
        } andThen (pushOut and pushIn) andThen function {
          mui.activeScreens.remove(oldScreen)
        }
      }
    }
    object slideOver {
      def up(oldScreen: Screen, newScreen: Screen, time: Double, easing: Easing = Easing.LinearIn): Transition = {
        apply(oldScreen, newScreen, time, easing, newScreen.position.y, -ui.height.get)
      }
      def down(oldScreen: Screen, newScreen: Screen, time: Double, easing: Easing = Easing.LinearIn): Transition = {
        apply(oldScreen, newScreen, time, easing, newScreen.position.y, ui.height.get)
      }
      def left(oldScreen: Screen, newScreen: Screen, time: Double, easing: Easing = Easing.LinearIn): Transition = {
        apply(oldScreen, newScreen, time, easing, newScreen.position.x, ui.width.get)
      }
      def right(oldScreen: Screen, newScreen: Screen, time: Double, easing: Easing = Easing.LinearIn): Transition = {
        apply(oldScreen, newScreen, time, easing, newScreen.position.x, -ui.width.get)
      }

      def apply(oldScreen: Screen,
                newScreen: Screen,
                time: Double,
                easing: Easing,
                inVar: Var[Double],
                inStart: => Double): Transition = {
        val pushIn = inVar transitionTo 0.0 in time easing easing
        function {
          inVar := inStart
          mui.activeScreens.add(newScreen)
        } andThen pushIn andThen function {
          mui.activeScreens.remove(oldScreen)
        }
      }
    }
  }
}