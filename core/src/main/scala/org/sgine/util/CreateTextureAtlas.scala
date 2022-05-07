package org.sgine.util

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.graphics.g2d.{PixmapPacker, PixmapPackerIO}
import com.badlogic.gdx.graphics.g2d.PixmapPacker.{GuillotineStrategy, PackStrategy}

import java.io.File

object CreateTextureAtlas {
  def apply(inputPath: String,
            atlasName: String = "texture.atlas",
            outputPath: String = "src/main/resources",
            pageWidth: Int = 1024,
            pageHeight: Int = 1024,
            pageFormat: Pixmap.Format = Pixmap.Format.RGBA8888,
            padding: Int = 2,
            duplicateBorder: Boolean = true,
            stripWhitespaceX: Boolean = false,
            stripWhitespaceY: Boolean = false,
            packStrategy: PackStrategy = new GuillotineStrategy): Unit = {
    val packer = new PixmapPacker(
      pageWidth, pageHeight, pageFormat, padding, duplicateBorder, stripWhitespaceX, stripWhitespaceY, packStrategy
    )
    val directory = new File(inputPath)
    directory.listFiles().foreach { file =>
      val name = file.getName.substring(0, file.getName.indexOf('.'))
      val image = new Pixmap(Gdx.files.absolute(file.getCanonicalPath))
      packer.pack(name, image)
    }
    val parameters = new PixmapPackerIO.SaveParameters
    parameters.format = PixmapPackerIO.ImageFormat.PNG
    parameters.magFilter = TextureFilter.Nearest
    parameters.minFilter = TextureFilter.Nearest
    parameters.useIndexes = true
    val io = new PixmapPackerIO
    io.save(Gdx.files.local(s"$outputPath/$atlasName"), packer, parameters)
  }
}
