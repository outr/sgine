package example

import com.badlogic.gdx.graphics.Texture
import org.sgine._
import org.sgine.lwjgl.BasicDesktopApp
import org.sgine.widget.Scale9

object Scale9Example extends BasicDesktopApp {
  lazy val texture: Texture = "scale9test.png"

  create.on {
    this += new Scale9 {
      position.center := ui.center
      position.middle := ui.middle
      size.width := 80.pctw
      size.height := 80.pcth

      slice(texture, 50, 50, 450, 450)
    }
  }
}