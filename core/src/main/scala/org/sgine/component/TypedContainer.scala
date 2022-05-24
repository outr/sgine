package org.sgine.component

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Group
import reactify._

trait TypedContainer[Child <: Component] extends ActorComponent[Group] { component =>
  def children: Children[Child]

  override protected def init(): Unit = {
    super.init()

    children.on {
      actor.getChildren.items.sortBy {
        case null => -1
        case actor => actor.getUserObject match {
          case null => -1
          case c: Component => children.indexOf(c)
        }
      }
    }
  }

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
    val vector = children.toVector
    val container = new TypedContainer[Child] { self =>
      override val children: Children[Child] = Children(self, vector)
    }
    container
  }
}