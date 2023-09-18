package examples

import org.sgine.audio.Audio
import org.sgine.PointerCursor
import org.sgine.UI.taskSupport
import org.sgine.component.{Container, Image, Label, PointerSupport}
import perfolation.double2Implicits
import reactify._
import org.sgine.task._

import scala.concurrent.duration.DurationInt

object AudioExample extends Example {
  private lazy val music = {
    val m = Audio.music("music.mp3")

    m.position.once(_ => {
      sequential(
        m.volume to 0.0 in 10.seconds,
        m.volume set 1.0,
      ).start
    }, condition = _ > 10.0)
    m
  }
  private lazy val sfx = Audio.sound("sfx.mp3")

  // TODO: DSL
  /*
  sequential(
    music.playFadeIn in 5.seconds,
    music.crossFade to music2 in 10.seconds at end,
    music2.fadeOutStop at 30.seconds
  )
   */

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