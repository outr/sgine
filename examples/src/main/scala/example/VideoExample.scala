package example

import org.sgine._
import org.sgine.lwjgl.BasicDesktopApp
import org.sgine.screen.FPSLoggingSupport
import org.sgine.video._

object VideoExample extends BasicDesktopApp with FPSLoggingSupport {
  this += new MediaPlayer {
    position.center := ui.width / 2.0
    position.middle := ui.height / 2.0

    size.maintainAspectRatio(width = ui.width)

    load("trailer_1080p.ogg")
    play()
  }
}