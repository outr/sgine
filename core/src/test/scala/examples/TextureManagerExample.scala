package examples
import org.sgine.component.{Component, Image}

object TextureManagerExample extends Example {
  override protected def root: Component = new Image(
    ExampleTextureManager.basketball
  )
}