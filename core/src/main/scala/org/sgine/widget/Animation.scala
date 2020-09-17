package org.sgine.widget

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode
import com.badlogic.gdx.graphics.g2d.{Animation => GDXAnimation, TextureRegion}
import org.sgine._
import org.sgine.component.gdx.{ActorIntegrated, EnhancedActor}
import org.sgine.component.prop.PreferredSize
import org.sgine.component.{ActorWidget, DimensionedComponent}
import reactify._

class Animation(implicit scrn: Screen) extends ActorWidget[EnhancedActor] with ActorIntegrated {
  override def screen: Screen = scrn

  private var elapsed = 0.0
  private var animation: GDXAnimation = _

  val frames: Var[Vector[TextureRegion]] = Var[Vector[TextureRegion]](Vector.empty)
  val frameDuration: Var[Double] = Var[Double](0.025)

  def framesFromTexture(texture: Texture, tileWidth: Int, tileHeight: Int): Unit = {
    val regions = TextureRegion.split(texture, tileWidth, tileHeight).flatten
    frames := regions.toVector
  }

  lazy val actor: EnhancedActor = new EnhancedActor {
    override def component: DimensionedComponent = Animation.this
  }

  frames.attach { f =>
    val (w, h) = f.headOption.map(tr => tr.getRegionWidth -> tr.getRegionHeight).getOrElse(0 -> 0)
    PreferredSize.update(preferred, w.toDouble, h.toDouble)
    elapsed = 0.0
    val playMode = PlayMode.NORMAL      // TODO: support multiple playmodes
    animation = new GDXAnimation(frameDuration.get.toFloat, f: _*)
    animation.setPlayMode(playMode)
  }

  frameDuration.attach { d =>
    if (animation != null) {
      animation.setFrameDuration(d.toFloat)
    }
  }

  render.on {
    val f = frames.get
    if (f.nonEmpty) {
      elapsed += ui.delta
      val looping = true
      val keyFrame = animation.getKeyFrame(elapsed.toFloat, looping)
      actor.batch.draw(keyFrame, actor.getX, actor.getY)
    }
  }
}
