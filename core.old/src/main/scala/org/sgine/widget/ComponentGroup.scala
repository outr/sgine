package org.sgine.widget

import com.badlogic.gdx.scenes.scene2d.Group
import org.sgine.Screen
import org.sgine.component._
import org.sgine.component.gdx.EnhancedActor

class ComponentGroup(implicit scrn: Screen) extends AbstractContainer with ActorWidget[Group with EnhancedActor] {
  override def screen: Screen = scrn

  override lazy val actor: Group with EnhancedActor = new Group with EnhancedActor {
    override def component: DimensionedComponent = ComponentGroup.this
  }

  override protected def add[C <: Component](child: C): C = {
    super.add(child)
    screen.render.once {
      child match {
        case ac: ActorComponent[_] => actor.addActor(ac.actor)
        case _ => // Ignore non-actor components
      }
    }
    child
  }

  override protected def remove[C <: Component](child: C): C = {
    super.remove(child)
    screen.render.once {
      child match {
        case ac: ActorComponent[_] => actor.removeActor(ac.actor)
        case _ => // Ignore non-actor components
      }
    }
    child
  }
}
