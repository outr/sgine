package org.sgine.component

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.{Actor, Group}
import org.sgine.render.{RenderContext, Renderable}
import com.badlogic.gdx.scenes.scene2d.ui.{Image => GDXImage}
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Scaling
import org.sgine.texture.Texture
import reactify.{Val, Var}

import scala.annotation.tailrec

trait RenderableComponent extends DimensionedComponent with Renderable {
  override def renderOrder: Double = z()

  override def shouldRender: Boolean = isVisible()
}

trait GroupContainer extends ActorComponent[Group] with Container {
  override lazy val actor: Group = new Group
}

trait ActorComponent[A <: Actor] extends DimensionedComponent {
  lazy val parentGroup: Val[Option[GroupContainer]] = Val(findParentGroup(parent))

  parentGroup.changes {
    case (oldValue, newValue) =>
      scribe.info(s"$actor - parentGroup changed: $oldValue -> $newValue")
      oldValue.foreach { pg =>
        pg.actor.removeActor(actor)
      }
      newValue.foreach { pg =>
        pg.actor.addActor(actor)
      }
  }

  @tailrec
  private def findParentGroup(parent: Option[Component]): Option[GroupContainer] = parent match {
    case None => None
    case Some(gc: GroupContainer) => Some(gc)
    case Some(p) => findParentGroup(p.parent())
  }

  def actor: A
}

class ImageComponent extends ActorComponent[Actor] {
  override lazy val actor: GDXImage = new GDXImage {
    setScaling(Scaling.stretch)
    setVisible(true)
    setX(0.0f)
    setY(0.0f)
    setWidth(2000)
    setHeight(485)
  }

  lazy val texture: Var[Texture] = {
    val v = Var(Texture.Pixel)
    v.attach { texture =>
      actor.setDrawable(new TextureRegionDrawable(texture.ref))
    }
    v
  }
}