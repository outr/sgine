package examples

import org.sgine.texture.{Texture, TextureManager}

/**
 * Auto-generated TextureManager. Do not directly modify.
 */
object ExampleTextureManager extends TextureManager(
  fileName = Some("src/test/scala/examples/ExampleTextureManager.scala"),
  name = "texture.atlas",
  path = "src/test/resources",
  inputPath = "../work/atlas"
) {
  lazy val airMinor: Vector[Texture] = byName("air-minor")
  lazy val basketball: Texture = oneByName("basketball")
}