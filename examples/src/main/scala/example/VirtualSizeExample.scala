package example

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import org.sgine._
import org.sgine.lwjgl.BasicDesktopApp
import org.sgine.screen.VirtualSizeSupport
import org.sgine.widget.Image

object VirtualSizeExample extends BasicDesktopApp with VirtualSizeSupport {
  create.on {
    this += new Image("1024.jpg") {
      position.x := 0.0.vx
      position.y := 0.0.vy
      size.width := 1024.0.vw
      size.height := 768.0.vh
    }
    this += new Image("sgine.png") {
      position.center := 512.0.vx
      position.middle := 385.0.vy
    }
  }

  override def init(config: LwjglApplicationConfiguration): Unit = {
    super.init(config)
    config.width = 1224
  }
}
