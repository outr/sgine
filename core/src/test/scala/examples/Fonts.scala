package examples

import com.badlogic.gdx.graphics.g2d.BitmapFont
import org.sgine.font.FontManager

object Fonts extends FontManager {
  object OpenSans {
    object Regular {
      lazy val (small, normal, large, extraLarge) = create("OpenSans-Regular.ttf") { generator =>
        (generator(48), generator(64), generator(100), generator(250))
      }
    }
  }
  object Pacifico {
    lazy val (normal, large) = create("Pacifico.ttf") { generator =>
      (generator(100), generator(200))
    }
  }
}