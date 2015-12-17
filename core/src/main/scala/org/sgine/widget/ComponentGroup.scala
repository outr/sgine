package org.sgine.widget

import com.badlogic.gdx.scenes.scene2d.Group
import org.sgine.Screen
import org.sgine.component.{AbstractContainer, ActorComponent, ActorWidget, Component}

class ComponentGroup(implicit scrn: Screen) extends AbstractContainer with ActorWidget[Group] {
  override def screen: Screen = scrn

  override lazy val actor: Group = new Group

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
