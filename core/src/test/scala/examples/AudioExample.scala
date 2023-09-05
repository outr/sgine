package examples

import org.sgine.audio.Audio
import org.sgine.{Color, PointerCursor}
import org.sgine.component.{Component, Container, Image, Label, PointerSupport}
import perfolation.double2Implicits

object AudioExample extends Example {
  private lazy val music = Audio.music("music.mp3")
  private lazy val sfx = Audio.sound("sfx.mp3")

  private lazy val logo = new Image("sgine.png") with PointerSupport {
    center @= screen.center
    middle @= screen.middle

    pointer.cursor @= Some(PointerCursor.Hand)
    pointer.isOver.attach {
      case true => music.play()
      case false => music.pause()
    }
    pointer.down.on {
      sfx.play()
    }
  }

  private lazy val musicStatus = new Label {
    font @= Fonts.OpenSans.Regular.normal
    center @= screen.center
    top @= 50.0
    text := s"Position: ${music.position().f()}"
  }

  override protected lazy val component: Container = Container(
    logo, musicStatus
  )
}