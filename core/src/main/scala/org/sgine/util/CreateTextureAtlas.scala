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
            pageWidth: Int = 2048,
            pageHeight: Int = 4096,
            pageFormat: Pixmap.Format = Pixmap.Format.RGBA8888,
            padding: Int = 2,
            duplicateBorder: Boolean = true,
            stripWhitespaceX: Boolean = false,
            stripWhitespaceY: Boolean = false,
            packStrategy: PackStrategy = new GuillotineStrategy): Unit = {
    val packer = new PixmapPacker(
      pageWidth, pageHeight, pageFormat, padding, duplicateBorder, stripWhitespaceX, stripWhitespaceY, packStrategy
    )

    def processDirectory(directory: File, prefix: String): Unit = {
      val files = directory.listFiles().toList.sortBy(_.getName)
      val fileCount = files.size
      files.zipWithIndex.foreach {
        case (file, index) =>
          if (file.isDirectory) {
            processDirectory(file, s"$prefix${file.getName.capitalize}")
          } else {
            val name = file.getName.substring(0, file.getName.indexOf('.')).capitalize
            val fullName = s"$prefix$name"
            val image = new Pixmap(Gdx.files.absolute(file.getCanonicalPath))
            scribe.info(s"Packing $fullName (${index + 1} of $fileCount)...")
            packer.pack(fullName, image)
          }
      }
    }
    processDirectory(new File(inputPath), "")

    val parameters = new PixmapPackerIO.SaveParameters
    parameters.format = PixmapPackerIO.ImageFormat.PNG
    parameters.magFilter = TextureFilter.Nearest
    parameters.minFilter = TextureFilter.Nearest
    parameters.useIndexes = true
    val io = new PixmapPackerIO
    io.save(Gdx.files.local(s"$outputPath/$atlasName"), packer, parameters)
    packer.dispose()
  }
}
