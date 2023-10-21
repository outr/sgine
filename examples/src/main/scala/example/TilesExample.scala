package example

import org.sgine.component.Component
import org.sgine.component.tile.{TiledComponent, TiledTextureComponent}
import org.sgine.drawable.Texture

object TilesExample extends Example {
  // TODO: Load from ExampleTextureManager
  override protected lazy val component: Component = new TiledTextureComponent(Texture.internal("background_desert.png"), overflow = true) {
    override type T = TiledComponent

    width @= 256.0 * 12.0
    height @= 2160.0

    override protected def create(tileX: Int, tileY: Int): TiledComponent = new TiledComponent(this, tileX, tileY)
  }
}