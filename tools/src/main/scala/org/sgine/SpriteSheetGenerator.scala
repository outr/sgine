package org.sgine

import java.io.File

import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.tools.texturepacker.TexturePacker
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings

object SpriteSheetGenerator {
  /**
    * Generates a SpriteSheet from the supplied input directory and writes it out to the supplied output directory.
    *
    * @param in the input directory to import images into the SpriteSheet from.
    * @param out the output directory to write the SpriteSheet to.
    * @param outName the name of the sprites file prefix. Defaults to "sprites".
    * @param mipMap whether to use mip-mapping. Defaults to true.
    * @param maxWidth the maximum texture width to use. Defaults to 2048.
    * @param maxHeight the maximum texture height to use. Defaults to 2048.
    */
  def generate(in: File,
               out: File,
               outName: String = "sprites",
               mipMap: Boolean = true,
               maxWidth: Int = 2048,
               maxHeight: Int = 2048) = {
    val settings = new Settings()
    settings.maxWidth = maxWidth
    settings.maxHeight = maxHeight
    settings.combineSubdirectories = true
    settings.pot = false
    settings.filterMin = if (mipMap) TextureFilter.MipMapLinearLinear else TextureFilter.Linear
    settings.filterMag = TextureFilter.Linear
    TexturePacker.process(settings, in.getCanonicalPath, out.getCanonicalPath, outName)
  }
}