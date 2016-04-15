package example

import org.powerscala.Color
import org.sgine._
import org.sgine.lwjgl.BasicDesktopApp
import org.sgine.transition.easing.Easing
import org.sgine.widget.{Container, Image, Rect}

object ContainerExample extends BasicDesktopApp {
  val container = new Container {
    position.x := 100.0
    position.y := 100.0
    size.width := 400.0
    size.height := 200.0

    this += new Rect {
      color := Color.Red
      size.width := 100.0
      size.height := 100.0
    }
    this += new Rect {
      color := Color.Green
      position.x := 100.0
      size.width := 100.0
      size.height := 100.0
    }
    this += new Rect {
      color := Color.Blue
      position.x := 200.0
      size.width := 100.0
      size.height := 100.0
    }
    this += new Rect {
      color := Color.White
      position.x := 300.0
      size.width := 100.0
      size.height := 100.0
    }
    this += new Image("sgine.png") {
      position.x := 0.0
      position.y := 100.0
    }

    (position.x transitionTo 600.0 in 1.5.seconds
      andThen(color.alpha transitionTo 0.2 in 1.5.seconds)
      andThen(color.alpha transitionTo 1.0 in 1.5.seconds)
      andThen(position.center transitionTo (ui.width.get / 2.0) in 1.5.seconds
        and(position.middle transitionTo (ui.height.get / 2.0) in 1.5.seconds)
        and(rotation transitionTo 360.0 in 1.5.seconds)
      )
      andThen(scale.x transitionTo 0.25 in 1.5.seconds easing Easing.ElasticOut
        and(scale.y transitionTo 0.25 in 1.5.seconds easing Easing.ElasticOut)
      )
      andThen(scale.x transitionTo 2.0 in 1.5.seconds easing Easing.ElasticOut
        and(scale.y transitionTo 2.0 in 1.5.seconds easing Easing.ElasticOut)
      )
    ).start(ui)
  }
  add(container)
}