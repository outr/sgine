package org.sgine.component

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Group
import reactify._

trait TypedContainer[Child <: Component] extends ActorComponent[Group] { component =>
  def children: Children[Child]

  override lazy val actor: Group = new Group {
    setUserObject(component)

    override def draw(batch: Batch, parentAlpha: Float): Unit = {
      render @= Gdx.graphics.getDeltaTime.toDouble
      super.draw(batch, parentAlpha)
    }

    override def act(delta: Float): Unit = {
      update(delta.toDouble)
      super.act(delta)
    }
  }
}

object TypedContainer {
  def apply[Child <: Component](children: Child*): TypedContainer[Child] = {
    val list = children.toList
    val container = new TypedContainer[Child] { self =>
      override val children: Children[Child] = Children(self, list)
    }
    container
  }

  def flatChildren(components: Component*): List[Component] = components.toList.flatMap {
    case container: TypedContainer[_] => container :: container.children.flatMap { child =>
      flatChildren(child)
    }
    case component => List(component)
  }
}