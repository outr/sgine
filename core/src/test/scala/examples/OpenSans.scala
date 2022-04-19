package examples

import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter
import org.sgine.font.FontManager

object OpenSans extends FontManager {
  object Regular {
    lazy val (small, normal, large) = create("OpenSans-Regular.ttf") { generator =>
      val param = new FreeTypeFontParameter
      param.size = 48
      val small = generator.generateFont(param)
      param.size = 64
      val normal = generator.generateFont(param)
      param.size = 100
      val large = generator.generateFont(param)
      (small, normal, large)
    }
  }
}
