package org.sgine.component

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.{Widget => GDXWidget}

trait ActorWidget[A <: Actor] extends Widget with ActorComponent[A] {
  size.width := preferred.width
  size.height := preferred.height

  def updatePreferredSize(): Unit = actor match {
    case w: GDXWidget => {
      if (w.getPrefWidth != 0.0) preferred.width(w.getPrefWidth)
      if (w.getPrefHeight != 0.0) preferred.height(w.getPrefHeight)
    }
    case _ => // Not a GDX Widget
  }

  screen.create.on {
    actor.setUserObject(ActorWidget.this)

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
    visible.attach(b => actor.setVisible(b))
  }
}