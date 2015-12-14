package example

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import org.sgine._
import org.sgine.lwjgl.BasicDesktopApp
import org.sgine.screen.{FPSLoggingSupport, VirtualMode, VirtualSizeSupport}
import org.sgine.widget.Rect

object RectExample extends BasicDesktopApp with VirtualSizeSupport with FPSLoggingSupport {
  override val virtualWidth: Double = 1000.0
  override val virtualHeight: Double = 1000.0
  override val virtualMode: VirtualMode = VirtualMode.Bars

  create.on {
    (0.0 until 1000.0 by 100.0).foreach { x =>
      (0.0 until 1000.0 by 100.0).foreach { y =>
        this += new Rect {
          position.x := x.vx
          position.y := y.vy
          size.width := 100.0.vw
          size.height := 100.0.vh
          color.red := math.random
          color.green := math.random
          color.blue := math.random
          forever(rotation transitionTo -360.0 in 2.seconds andThen function(rotation := 0.0)).start()
        }
      }
    }
  }

  override def init(config: LwjglApplicationConfiguration): Unit = {
    super.init(config)
    config.vSyncEnabled = false
  }
}
