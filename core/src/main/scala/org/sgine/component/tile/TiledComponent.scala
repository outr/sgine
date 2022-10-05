package org.sgine.component.tile

import org.sgine.component._

class TiledComponent(tiled: TiledTextureComponent,
                     tileX: Int,
                     tileY: Int) extends Container with DimensionedSupport { self =>
  protected lazy val texture: Image = {
    x @= tileX * tiled.tileWidth
    y @= tileY * tiled.tileHeight
    width @= tiled.tileWidth
    height @= tiled.tileHeight

    val tc = new Image
    tc.drawable @= tiled.texture
    tc
  }

  override val children: Children[Component] = Children(this, Vector(texture))

  override def toString: String = s"TiledComponent(texture = ${tiled.texture}, tileX = $tileX, tileY = $tileY)"
}
