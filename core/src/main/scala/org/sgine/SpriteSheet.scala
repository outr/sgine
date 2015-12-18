package org.sgine

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion

trait SpriteSheet {
  protected def atlas: TextureAtlas

  protected def internal(filename: String): TextureAtlas = new TextureAtlas(Gdx.files.internal(filename))
  protected def external(filename: String): TextureAtlas = new TextureAtlas(Gdx.files.external(filename))
  protected def classpath(filename: String): TextureAtlas = new TextureAtlas(Gdx.files.classpath(filename))

  def apply(name: String): AtlasRegion = atlas.findRegion(name)
}