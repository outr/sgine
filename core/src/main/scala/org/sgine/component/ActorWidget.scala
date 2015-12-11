package org.sgine.component

import com.badlogic.gdx.scenes.scene2d.Actor

trait ActorWidget[A <: Actor] extends Widget with ActorComponent[A] {
  screen.create.on {
    position.x.attach(d => actor.setX(d.toFloat))
    position.y.attach(d => actor.setY(d.toFloat))
    size.width.attach(d => actor.setWidth(d.toFloat))
    size.height.attach(d => actor.setHeight(d.toFloat))
    color.red.attach(d => actor.getColor.r = d.toFloat)
    color.green.attach(d => actor.getColor.g = d.toFloat)
    color.blue.attach(d => actor.getColor.b = d.toFloat)
    color.alpha.attach(d => actor.getColor.a = d.toFloat)
    rotation.attach(d => actor.setRotation(d.toFloat))
    origin.x.attach(d => actor.setOriginX(d.toFloat))
    origin.y.attach(d => actor.setOriginY(d.toFloat))
    scale.x.attach(d => actor.setScaleX(d.toFloat))
    scale.y.attach(d => actor.setScaleY(d.toFloat))
  }
}