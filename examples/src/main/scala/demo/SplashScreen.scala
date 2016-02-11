package demo

import org.sgine._
import org.sgine.transition.easing.Easing
import org.sgine.widget.{Image, Rect}

object SplashScreen extends Screen with VirtualSizeSupport {
  virtualWidth := 1920.0
  virtualHeight := 1080.0
  virtualMode := VirtualMode.Bars

  val background = new Rect {
    size.width := ui.width
    size.height := ui.height
    color.alpha := 0.0
    colors.topLeft := Color.DarkBlue
    colors.topRight := Color.DarkBlue
    colors.bottomLeft := Color.Black
    colors.bottomRight := Color.Black
  }

  val logo = new Image("sgine.png") {
    position.center := ui.center
    position.bottom := ui.height
    scale := 0.0
    color.alpha := 0.0
  }

  add(background)
  add(logo)

  val fadeIn = logo.color.alpha transitionTo 1.0 in 0.5
  val moveDown = logo.position.middle transitionTo ui.middle.get in 1.0 easing Easing.BackOut
  val scaleUpX = logo.scale.x transitionTo 1.5 in 1.0
  val scaleUpY = logo.scale.y transitionTo 1.5 in 1.0
  val bringLogoIn = fadeIn and moveDown and scaleUpX and scaleUpY

  val gradientFadeIn = background.color.alpha transitionTo 1.0 in 0.5

//  gradientFadeIn start ui
  bringLogoIn andThen gradientFadeIn andThen function {
    logo.position.middle := ui.middle
  } start ui
}