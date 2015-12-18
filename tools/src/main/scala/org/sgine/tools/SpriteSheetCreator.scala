package org.sgine.tools

import java.io.File

import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.tools.texturepacker.TexturePacker
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings

/**
 * @author Matt Hicks <matt@outr.com>
 */
object SpriteSheetCreator {
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