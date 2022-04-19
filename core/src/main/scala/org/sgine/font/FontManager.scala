package org.sgine.font

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator

trait FontManager {
  protected def create[Return](path: String)
                              (f: FreeTypeFontGenerator => Return): Return = {
    val generator = new FreeTypeFontGenerator(Gdx.files.internal(path))
    try {
      f(generator)
    } finally {
      generator.dispose()
    }
  }
}