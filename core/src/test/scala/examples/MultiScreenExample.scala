package examples

import org.sgine.component._
import org.sgine.{Color, App, Screen, UI}
import reactify._

object MultiScreenExample extends App {
  private lazy val screen1: Screen = new SimpleScreen("Screen 1", Color.Red, screen2)
  private lazy val screen2: Screen = new SimpleScreen("Screen 2", Color.Green, screen3)
  private lazy val screen3: Screen = new SimpleScreen("Screen 3", Color.Blue, screen1)

  override protected def init(): Unit = {
    FPSView.font @= Fonts.OpenSans.Regular.normal

    UI.screen @= screen1
  }

  class SimpleScreen(label: String, screenColor: Color, nextScreen: => Screen) extends Screen { screen =>
    override protected lazy val component: Component = {
      screen.width.attach { d =>
        scribe.info(s"$label width changed: $d")
      }
      Container(
        new Rectangle {
          width := screen.width
          height := screen.height
          color @= screenColor
        },
        new Image("crate.jpg") with InteractiveComponent {
          center @= screen.width / 2.0
          middle @= screen.height / 2.0
          color := (if (pointer.over) screenColor else Color.White)
          pointer.down.on {
            UI.screens.transition.crossFade(nextScreen)
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
