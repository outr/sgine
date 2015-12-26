package example

import org.sgine._
import org.sgine.input.Key
import org.sgine.lwjgl.BasicDesktopApp
import org.sgine.screen.FPSLoggingSupport
import org.sgine.video._
import org.sgine.widget.Label

object VideoExample extends BasicDesktopApp with FPSLoggingSupport {
  val mediaPlayer = new MediaPlayer {
    position.center := ui.center
    position.middle := ui.middle

    size.maintainAspectRatio(width = ui.width)

    load("trailer_1080p.ogg")
    play()
  }
  val mediaTime = new Label("---", "OpenSans", "Regular", 36) {
    position.center := ui.center
    position.top := mediaPlayer.position.bottom - 50.0
  }

  mediaPlayer.status.time.attach(time => mediaTime.text := s"$time seconds (${math.round(mediaPlayer.status.position.get * 100.0)}%)")
  key.down.attach { k =>
    k.key match {
      case Key.Space | Key.Pause => mediaPlayer.pause()
      case Key.Left => mediaPlayer.jump(-5.0)
      case Key.Right => mediaPlayer.jump(5.0)
      case _ => // Nothing bound
    }
  }

  add(mediaPlayer)
  add(mediaTime)
}