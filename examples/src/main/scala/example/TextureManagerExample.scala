package example

import org.sgine.component.{Component, Image}

object TextureManagerExample extends Example {
  override protected def component: Component = new Image(
    ExampleTextureManager.basketball
  )
}