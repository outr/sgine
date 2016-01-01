package example

import com.badlogic.gdx.Gdx
import org.sgine.lwjgl.BasicDesktopApp
import org.sgine.tools.Scale9Trimmer

object Scale9TrimmerExample extends BasicDesktopApp {
  create.on {
    Scale9Trimmer.trim(Gdx.files.classpath("browse_box.png"))
  }
}