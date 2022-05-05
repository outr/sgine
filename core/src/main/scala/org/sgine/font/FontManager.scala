package org.sgine.font

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter

trait FontManager {
  type Generator = Int => BitmapFont

  protected def create[Return](path: String)
                              (f: Generator => Return): Return = {
    val ftfg = new FreeTypeFontGenerator(Gdx.files.internal(path))
    try {
      val generator = (size: Int) => {
        val param = new FreeTypeFontParameter
        param.size = size
        ftfg.generateFont(param)
      }
      f(generator)
    } finally {
      ftfg.dispose()
    }
  }
}