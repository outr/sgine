package example

import org.sgine.Key
import org.sgine.component.{Component, Image}
import reactify._

object KeyExample extends Example {
  private val step = 50.0

  override protected def component: Component = new Image("crate.jpg") {
    center @= screen.center
    middle @= screen.middle

    screen.input.keyTyped.onKey(Key.Up) {
      y @= y - step
    }
    screen.input.keyTyped.onKey(Key.Down) {
      y @= y + step
    }
    screen.input.keyTyped.onKey(Key.Left) {
      x @= x - step
    }
    screen.input.keyTyped.onKey(Key.Right) {
      x @= x + step
    }
  }
}
