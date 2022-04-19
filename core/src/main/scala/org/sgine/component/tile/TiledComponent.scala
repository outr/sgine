package org.sgine.component.tile

import org.sgine.component._

class TiledComponent(tiled: TiledTextureComponent, tileX: Int, tileY: Int) extends Container with DimensionedComponent {
  protected lazy val texture: TextureView = {
    x @= tileX * tiled.tileWidth
    y @= tileY * tiled.tileHeight
    width @= tiled.tileWidth
    height @= tiled.tileHeight

    val tc = new TextureView
    tc.texture @= tiled.texture
    tc
  }

  override val children: Children[Component] = Children(this, List(texture))

  override def toString: String = s"TiledComponent(texture = ${tiled.texture}, tileX = $tileX, tileY = $tileY)"
}
