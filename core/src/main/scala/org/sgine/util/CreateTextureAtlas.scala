package org.sgine.util

import com.badlogic.gdx.graphics.Texture.{TextureFilter, TextureWrap}
import com.badlogic.gdx.tools.texturepacker.TexturePacker

object CreateTextureAtlas {
  def apply(inputPath: String,
            atlasName: String = "texture.atlas",
            outputPath: String = "src/main/resources"): Unit = {
    val settings = new TexturePacker.Settings
    settings.minWidth = 16
    settings.minHeight = 16
    settings.maxWidth = 2048
    settings.maxHeight = 4096
    settings.alphaThreshold = 0
    settings.filterMin = TextureFilter.Nearest
    settings.filterMag = TextureFilter.Nearest
    settings.paddingX = 2
    settings.paddingY = 2
    settings.wrapX = TextureWrap.ClampToEdge
    settings.wrapY = TextureWrap.ClampToEdge
    settings.bleed = true
    settings.legacyOutput = false
    scribe.info(s"Processing $inputPath...")
    TexturePacker.process(settings, inputPath, outputPath, atlasName)
  }
}
