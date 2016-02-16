package demo

import org.sgine._
import org.sgine.screen.FPSLoggingSupport
import org.sgine.transition.easing.Easing
import org.sgine.widget.Image

object SplashScreen extends Screen with VirtualSizeSupport with FPSLoggingSupport {
  virtualWidth := 1920.0
  virtualHeight := 1080.0
  virtualMode := VirtualMode.Bars

  val logo = new Image("sgine_highres.png") {
    position.center := ui.center
    position.bottom := ui.height
    scale := 0.0
    color.alpha := 0.0
    size.maintainAspectRatio(width = 1000.vw)
  }

  add(logo)

  val initialDelay = delay(0.1)

  val fadeIn = logo.color.alpha transitionTo 1.0 in 2.0
  val moveDown = logo.position.middle transitionTo ui.middle.get in 2.0 easing Easing.BackOut
  val scaleUpX = logo.scale.x transitionTo 1.5 in 2.0
  val scaleUpY = logo.scale.y transitionTo 1.5 in 2.0
  val bringLogoIn = fadeIn and moveDown and scaleUpX and scaleUpY

  val betweenDelay = delay(1.0)

  val rotateOut = logo.rotation transitionTo 720.0 in 3.0 easing Easing.CircularIn
  val scaleDownX = logo.scale.x transitionTo 0.0 in 3.0
  val scaleDownY = logo.scale.y transitionTo 0.0 in 3.0
  val fadeOut = logo.color.alpha transitionTo 0.0 in 3.5
  val takeLogoOut = rotateOut and scaleDownX and scaleDownY and fadeOut

  initialDelay andThen bringLogoIn andThen betweenDelay andThen takeLogoOut start ui
}