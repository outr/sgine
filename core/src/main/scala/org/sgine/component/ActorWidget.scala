package org.sgine.component

import com.badlogic.gdx.scenes.scene2d.Actor

trait ActorWidget[A <: Actor] extends Widget with ActorComponent[A] {
  position.x.attach(d => actor.setX(d.toFloat))
  position.y.attach(d => actor.setY(d.toFloat))
  size.width.attach(d => actor.setWidth(d.toFloat))
  size.height.attach(d => actor.setHeight(d.toFloat))
}