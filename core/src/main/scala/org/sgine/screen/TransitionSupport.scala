package org.sgine.screen

import org.sgine._
import org.sgine.render.MultiScreenSupport
import org.sgine.transition.Transition
import org.sgine.transition.easing.Easing
import pl.metastack.metarx.Sub

trait TransitionSupport extends Screen {
  object transitions {
    private def mui = ui.asInstanceOf[MultiScreenSupport]

    object fade {
      def cross(screen: Screen,
                time: Double,
                easing: Easing = Easing.LinearIn,
                add: Boolean = true,
                remove: Boolean = true): Transition = {
        val fadeOut = out(screen, time, easing, remove)
        val fadeIn = in(thisScreen, time, easing, add)
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
      def up(out: Screen, time: Double, easing: Easing = Easing.LinearIn): Transition = {
        apply(out, time, easing, position.y, out.position.y, ui.height.get)
      }
      def down(out: Screen, time: Double, easing: Easing = Easing.LinearIn): Transition = {
        apply(out, time, easing, position.y, out.position.y, -ui.height.get)
      }
      def left(out: Screen, time: Double, easing: Easing = Easing.LinearIn): Transition = {
        apply(out, time, easing, position.x, out.position.x, -ui.width.get)
      }
      def right(out: Screen, time: Double, easing: Easing = Easing.LinearIn): Transition = {
        apply(out, time, easing, position.x, out.position.x, ui.width.get)
      }

      def apply(screen: Screen,
                time: Double,
                easing: Easing,
                inSub: Sub[Double],
                outSub: Sub[Double],
                outDestination: => Double): Transition = {
        val pushOut = outSub transitionTo outDestination in time easing easing
        val pushIn = inSub transitionTo 0.0 in time easing easing
        function {
          inSub := -outDestination
          mui.activeScreens.add(thisScreen)
        } andThen (pushOut and pushIn) andThen function {
          mui.activeScreens.remove(screen)
        }
      }
    }
    object slideOver {
      def up(out: Screen, time: Double, easing: Easing = Easing.LinearIn): Transition = {
        apply(out, time, easing, position.y, -ui.height.get)
      }
      def down(out: Screen, time: Double, easing: Easing = Easing.LinearIn): Transition = {
        apply(out, time, easing, position.y, ui.height.get)
      }
      def left(out: Screen, time: Double, easing: Easing = Easing.LinearIn): Transition = {
        apply(out, time, easing, position.x, ui.width.get)
      }
      def right(out: Screen, time: Double, easing: Easing = Easing.LinearIn): Transition = {
        apply(out, time, easing, position.x, -ui.width.get)
      }

      def apply(screen: Screen,
                time: Double,
                easing: Easing,
                inSub: Sub[Double],
                inStart: => Double): Transition = {
        val pushIn = inSub transitionTo 0.0 in time easing easing
        function {
          inSub := inStart
          mui.activeScreens.add(thisScreen)
        } andThen pushIn andThen function {
          mui.activeScreens.remove(screen)
        }
      }
    }
  }
}
