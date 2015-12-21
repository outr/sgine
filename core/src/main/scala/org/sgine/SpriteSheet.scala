package org.sgine

import java.io.File

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import com.badlogic.gdx.tools.texturepacker.TexturePacker
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings

trait SpriteSheet {
  protected def atlas: TextureAtlas

  protected def internal(filename: String): TextureAtlas = new TextureAtlas(Gdx.files.internal(filename))
  protected def external(filename: String): TextureAtlas = new TextureAtlas(Gdx.files.external(filename))
  protected def classpath(filename: String): TextureAtlas = new TextureAtlas(Gdx.files.classpath(filename))

  def apply(name: String): AtlasRegion = atlas.findRegion(name)
}

object SpriteSheet {
  def generate(in: File, out: File, outName: String = "sprites", mipMap: Boolean = true) = {
    val settings = new Settings()
    settings.maxWidth = 2048
    settings.maxHeight = 2048
    settings.combineSubdirectories = true
    settings.pot = false
    settings.filterMin = if (mipMap) TextureFilter.MipMapLinearLinear else TextureFilter.Linear
    settings.filterMag = TextureFilter.Linear
    TexturePacker.process(settings, in.getCanonicalPath, out.getCanonicalPath, outName)
  }
}