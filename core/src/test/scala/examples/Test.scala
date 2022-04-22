package examples
import org.sgine.component.{Component, ImageComponent}
import org.sgine.texture.Texture

object Test extends Example {
  override protected lazy val root: Component = new ImageComponent {
    texture @= Texture.internal("sgine.png")
  }
}
