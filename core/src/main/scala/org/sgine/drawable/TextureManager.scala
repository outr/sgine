package org.sgine.drawable

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import org.sgine.util.CreateTextureAtlas

import java.io.File
import java.nio.file.Files
import scala.collection.mutable
import scala.util.Try

/**
 * Type-safe texture manager with support for dev-mode texture atlas regeneration.
 *
 * Warning: if fileName is set, the sub-class will be regenerated automatically, so don't customize the code.
 *
 * @param fileName the relative path the TextureManager sub-class to regenerate. If None, no auto generation will occur.
 * @param name the texture atlas filename (defaults to "texture.atlas")
 * @param path the path where the texture atlas file is stored
 * @param inputPath where the original images are stored that should be used to create the atlas
 * @param scaleOverride defines a mapping of texture names to override scale
 */
abstract class TextureManager(fileName: Option[String],
                              name: String = "texture.atlas",
                              path: String = "src/main/resources",
                              inputPath: String = "resources",
                              pageWidth: Int = 1024,
                              pageHeight: Int = 1024,
                              scaleOverride: Map[String, Double] = Map.empty) {
  private lazy val atlas: Map[String, Vector[Texture]] = Try(new TextureAtlas(Gdx.files.internal(name))
    .getRegions
    .toArray
    .toList
    .groupBy(_.name)
    .map {
      case (name, regions) =>
        val vector = regions
          .sortBy(_.index)
          .map(Texture.apply(_))
          .toVector
        name -> vector
    }).toEither match {
    case Left(t) =>
      scribe.warn(s"Error loading atlas: ${t.getMessage}")
      Map.empty
    case Right(map) => map
  }

  private var initialized = false

  private def init(): Unit = synchronized {
    if (!initialized) {
      initialized = true

      fileName.foreach(regen)
    }
  }

  private def regen(path: String): Unit = {
    val textureManagerSource = new File(path)
    if (!textureManagerSource.isFile) {
      scribe.warn(s"Unable to find file: ${textureManagerSource.getAbsolutePath}. No regeneration will be done.")
    } else {
      val atlasFile = new File(this.path, name)
      val inputDirectory = new File(inputPath)
      if (!inputDirectory.isDirectory) throw new RuntimeException(s"Input directory does not exist: ${inputDirectory.getAbsolutePath}")
      val outputPath = new File(this.path)
      outputPath.mkdirs()

      def recurseFiles(dir: File, prefix: String): List[(File, String)] = {
        val files = dir.listFiles().toList
        files.flatMap { file =>
          if (file.isDirectory) {
            recurseFiles(file, s"$prefix${file.getName.capitalize}")
          } else {
            List((file, s"$prefix${file.getName.capitalize}"))
          }
        }
      }
      val inputFiles = recurseFiles(inputDirectory, "")
      val atlasTextureCount = atlas.flatMap(_._2).size
      val newestFileTime = inputFiles.map(_._1.lastModified()).max
      val shouldRegen = if (atlasTextureCount != inputFiles.length) {
        scribe.info(s"Atlas texture count ($atlasTextureCount) is not the same as the input file count (${inputFiles.length}). Regenerating...")
        true
      } else if (newestFileTime > atlasFile.lastModified()) {
        scribe.info(s"A file has been updated since atlas was generated. Regenerating...")
        true
      } else {
        false
      }
      if (!getClass.getSimpleName.endsWith("$")) throw new RuntimeException(s"TextureManager sub-class must be an object")
      if (shouldRegen) {
        // Create the texture atlas
        CreateTextureAtlas(
          inputPath = inputPath,
          atlasName = name,
          outputPath = this.path,
          pageWidth = pageWidth,
          pageHeight = pageHeight
        )

        // Regenerate the TextureManager code
        val packageName = getClass.getPackage.getName
        val className = getClass.getSimpleName.replace("$", "")
        val IndexedRegex = """(.+)_(\d{1,2})""".r

        val textureMappings = inputFiles.groupBy {
          case (_, fileName) =>
            fileName.substring(0, fileName.indexOf('.')) match {
              case IndexedRegex(name, _) => name
              case s => s
            }
        }
        val textureNames = textureMappings.keys.toList.sorted
        val textureEntries = textureNames.map { textureName =>
          val refName = {
            var p = ' '
            val b = new mutable.StringBuilder()
            textureName.foreach { c =>
              if (p == '_' || p == '-') {
                b.append(c.toUpper)
              } else if (c == '_' || c == '-') {
                // Ignore
              } else {
                b.append(c)
              }
              p = c
            }
            b.toString()
          }
          val isMulti = textureMappings(textureName).length > 1
          if (isMulti) {
            s"""  lazy val $refName: Vector[Texture] = byName("$textureName")"""
          } else {
            s"""  lazy val $refName: Texture = oneByName("$textureName")"""
          }
        }
        val scaleOverrideString = if (scaleOverride.isEmpty) {
          "Map.empty"
        } else {
          val pairs = scaleOverride.map {
            case (key, value) => s"""    "$key" -> $value"""
          }.mkString(",\n")
          s"""Map(
             |$pairs
             |  )""".stripMargin
        }
        val source =
          s"""package $packageName
             |
             |import org.sgine.drawable.{Texture, TextureManager}
             |
             |/**
             | * Auto-generated TextureManager. Do not directly modify.
             | */
             |object $className extends TextureManager(
             |  fileName = Some("$path"),
             |  name = "$name",
             |  path = "${this.path}",
             |  inputPath = "$inputPath",
             |  pageWidth = $pageWidth,
             |  pageHeight = $pageHeight,
             |  scaleOverride = $scaleOverrideString
             |) {
             |${textureEntries.mkString("\n")}
             |}""".stripMargin
        Files.write(textureManagerSource.toPath, source.getBytes("UTF-8"))

        scribe.warn("Regeneration successful. Terminating to allow re-build.")
        sys.exit(0)
      }
    }
  }

  def oneByName(name: String): Texture = byName(name)(0)
  def byName(name: String): Vector[Texture] = {
    init()
    val textures = atlas(name)
    val scale = scaleOverride.getOrElse(name, 1.0)
    if (scale == 1.0) {
      textures
    } else {
      textures.map(_.scaled(scale))
    }
  }
}