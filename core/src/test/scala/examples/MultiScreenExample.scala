package examples

import org.sgine.component.{Component, Container, Image, InteractiveComponent, Label}
import org.sgine.easing.Easing
import org.sgine.task._
import org.sgine.{Color, MultiScreenApp, Screen, UI}

import scala.concurrent.duration.DurationInt

object MultiScreenExample extends MultiScreenApp {
  private lazy val screen1: Screen = new SimpleScreen("Screen 1", Color.Red, screen2)
  private lazy val screen2: Screen = new SimpleScreen("Screen 2", Color.Green, screen3)
  private lazy val screen3: Screen = new SimpleScreen("Screen 3", Color.Blue, screen1)

  override protected def init(): Unit = {
    UI.fpsFont @= Fonts.OpenSans.Regular.normal

    UI.screen @= screen1
  }

  class SimpleScreen(label: String, screenColor: Color, nextScreen: => Screen) extends Screen { screen =>
    override protected lazy val root: Component = {
      screen.width.attach { d =>
        scribe.info(s"$label width changed: $d")
      }
      Container(
        new Image("crate.jpg") with InteractiveComponent {
          center @= screen.width / 2.0
          middle @= screen.height / 2.0
          color := (if (pointer.over) screenColor else Color.White)
          pointer.down.on {
            nextScreen.left @= nextScreen.width
            UI.screens += nextScreen
            sequential(
              parallel(
                nextScreen.left to 0.0 in 1.seconds easing Easing.bounceOut,
                screen.right to 0.0 in 1.seconds easing Easing.bounceOut
              ),
              synchronous(UI.screens -= screen)
            ).start
          }
        },
        new Label {
          center @= screen.width / 2.0
          top @= 100.0
          font @= Fonts.Pacifico.normal
          text @= label
        }
      )
    }
  }
}
