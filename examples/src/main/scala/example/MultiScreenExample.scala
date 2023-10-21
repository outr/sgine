package example

import org.sgine.component._
import org.sgine.task._
import org.sgine.{Color, Screen, SgineApp, UI}
import reactify._

import scala.concurrent.duration.DurationInt

object MultiScreenExample extends SgineApp {
  private lazy val screen1: Screen = new SimpleScreen("Screen 1", Color.Red, screen2)
  private lazy val screen2: Screen = new SimpleScreen("Screen 2", Color.Green, screen3)
  private lazy val screen3: Screen = new SimpleScreen("Screen 3", Color.Blue, screen1)

  override def init(): Unit = {
    FPSView.font @= Fonts.OpenSans.Regular.normal

    UI.screens.set(screen1)
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
        new Image("crate.jpg") with PointerSupport {
          center @= screen.width / 2.0
          middle @= screen.height / 2.0
          color := (if (pointer.isOver) screenColor else Color.White)
          pointer.down.on {
            UI.screens @= nextScreen
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

    override def activate(): Task = {
      color @= Color.Clear
      color to Color.White in 1.second
    }

    override def deactivate(): Task = color to Color.Clear in 1.second

    override def toString: String = label
  }
}
