package org.sgine.font

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter

trait FontManager {
  type Generator = Int => BitmapFont

  protected def create[Return](path: String, allowMarkup: Boolean = true)
                              (f: Generator => Return): Return = {
    val ftfg = new FreeTypeFontGenerator(Gdx.files.internal(path))
    try {
      val generator = (size: Int) => {
        val param = new FreeTypeFontParameter
        param.size = size
        val font = ftfg.generateFont(param)
        font.getData.markupEnabled = allowMarkup
        font
      }
      f(generator)
    } finally {
      ftfg.dispose()
    }
  }
}