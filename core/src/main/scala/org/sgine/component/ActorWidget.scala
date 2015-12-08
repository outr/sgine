package org.sgine.component

import com.badlogic.gdx.scenes.scene2d.Actor

trait ActorWidget[A <: Actor] extends Widget with ActorComponent[A] {
  position.x.map(d => actor.setX(d.toFloat))
  position.y.map(d => actor.setY(d.toFloat))
  size.width.map(d => actor.setWidth(d.toFloat))
  size.height.map(d => actor.setHeight(d.toFloat))
}
