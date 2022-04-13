package org.sgine.tools

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.graphics.g2d.BitmapFont.BitmapFontData
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter
import com.badlogic.gdx.graphics.g2d.{BitmapFont, PixmapPacker, TextureRegion}
import com.badlogic.gdx.graphics.glutils.PixmapTextureData
import com.badlogic.gdx.graphics.{Pixmap, Texture}
import com.badlogic.gdx.tools.bmfont.BitmapFontWriter
import com.badlogic.gdx.tools.bmfont.BitmapFontWriter.FontInfo
import org.sgine.UI

class BitmapFontManager {
  private val pageSize = 1024

  def createFont(fontFile: FileHandle, fontName: String, fontSize: Int): BitmapFont = {
    generateFont(fontName, fontFile, fontSize, pageSize, pageSize)
  }

  def generateFont(fontName: String, fontFile: FileHandle, fontSize: Int, pageWidth: Int, pageHeight: Int): BitmapFont = {
    val generator = new FreeTypeFontGenerator(fontFile)
    val padding = 2
    val duplicateBorder = false
    val packer = new PixmapPacker(pageWidth, pageHeight, Pixmap.Format.RGBA8888, padding, duplicateBorder)
    val param = new FreeTypeFontParameter
    param.genMipMaps = true
    param.size = fontSize
    param.packer = packer
    val fontData: BitmapFontData = generator.generateData(param)
    val pages = packer.getPages
    val texRegions = new com.badlogic.gdx.utils.Array[TextureRegion](pages.size)
    val useMipMaps = true
    val disposePixmap = false
    val minFilter = TextureFilter.MipMapLinearLinear
    val magFilter = TextureFilter.Linear
    val flip = false
    (0 until pages.size).foreach { index =>
      val page = pages.get(index)
      val tex = new Texture(new PixmapTextureData(page.getPixmap, page.getPixmap.getFormat, useMipMaps, disposePixmap)) {
        override def dispose(): Unit = {
          super.dispose()
          getTextureData.consumePixmap().dispose()
        }
      }
      tex.setFilter(minFilter, magFilter)
      texRegions.add(new TextureRegion(tex))
    }
    val font = new BitmapFont(fontData, texRegions, flip)
    saveFontToFile(font, fontSize, fontName, packer)
    generator.dispose()
    packer.dispose()
    font
  }

  def saveFontToFile(font: BitmapFont, fontSize: Int, fontName: String, packer: PixmapPacker): Unit = try {
    val filename = s"$fontName-$fontSize"
    val directory = Gdx.files.local("fonts")
    directory.mkdirs()
    val fontFile = directory.child(s"$filename.fnt")
    BitmapFontWriter.setOutputFormat(BitmapFontWriter.OutputFormat.Text)

    val pageRefs = BitmapFontWriter.writePixmaps(packer.getPages, directory, filename)
    val scaleW = 1
    val scaleH = 1
    BitmapFontWriter.writeFont(font.getData, pageRefs, fontFile, new FontInfo(fontName, fontSize), scaleW, scaleH)
  } catch {
    case t: Throwable => UI().warn(t, Some("Unable to write font to file."))
  }
}