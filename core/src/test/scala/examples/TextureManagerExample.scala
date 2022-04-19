package examples
import org.sgine.component.{Component, TextureView}

object TextureManagerExample extends Example {
  override protected def root: Component = new TextureView(
    ExampleTextureManager.basketball
  )
}