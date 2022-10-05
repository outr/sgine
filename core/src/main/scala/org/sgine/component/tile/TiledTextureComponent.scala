package org.sgine.component.tile

import org.sgine.component.{Children, Component, Container}
import org.sgine.drawable.Texture
import reactify._

abstract class TiledTextureComponent(val texture: Texture, overflow: Boolean) extends Container {
  type T <: TiledComponent

  protected def create(tileX: Int, tileY: Int): T

  val tileWidth: Double = texture.width * texture.scaleX
  val tileHeight: Double = texture.height * texture.scaleY
  lazy val horizontal: Int = width / tileWidth match {
    case d if overflow => math.ceil(d).toInt
    case d => math.floor(d).toInt
  }
  lazy val vertical: Int = height / tileHeight match {
    case d if overflow => math.ceil(d).toInt
    case d => math.floor(d).toInt
  }

  lazy val tiles: Vector[Vector[T]] = {
    scribe.info(s"Building tiles for $horizontal x $vertical - tile: $tileWidth x $tileHeight")
    (0 until vertical).toVector.map { tileY =>
      (0 until horizontal).toVector.map { tileX =>
        create(tileX, tileY)
      }
    }
  }

  def apply(tileX: Int, tileY: Int): T = tiles(tileY)(tileX)

  lazy val flatTiles: Vector[T] = tiles.flatten

  override lazy val children: Children[Component] = Children(this, flatTiles)
}