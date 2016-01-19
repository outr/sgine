package example

import com.badlogic.gdx.Gdx
import org.sgine._
import org.sgine.input.Key
import org.sgine.lwjgl.BasicDesktopApp
import org.sgine.screen.FPSLoggingSupport
import org.sgine.video._
import org.sgine.widget.Label
import pl.metastack.metarx._

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
    position.bottom := 15.0
  }
  val mediaTimeShadow = new Label("---", "OpenSans", "Regular", 36) {
    position.center := ui.center + 2.0
    position.bottom := 13.0
    color := Color.Black
    color.alpha := 0.5
  }

  render.on {
    val time = mediaPlayer.status.time.get
    val text = s"$time seconds (${math.min(100, math.round(mediaPlayer.status.position.get * 100.0))}% - ${mediaPlayer.state.get})"
    mediaTime.text := text
    mediaTimeShadow.text := text
  }
  key.down.attach { k =>
    k.key match {
      case Key.Space | Key.Pause => mediaPlayer.pause()
      case Key.Left => mediaPlayer.jump(-5.0)
      case Key.Right => mediaPlayer.jump(5.0)
      case _ => // Nothing bound
    }
  }
  mediaPlayer.onState(PlayerState.Finished) {
    Gdx.app.exit()
  }

  add(mediaPlayer)
  add(mediaTimeShadow)
  add(mediaTime)
}