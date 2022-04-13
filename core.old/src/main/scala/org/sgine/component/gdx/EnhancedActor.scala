package org.sgine.component.gdx

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack
import org.sgine.component.DimensionedComponent

trait EnhancedActor extends Actor {
  private var _batch: Batch = _
  def batch: Batch = _batch
  def component: DimensionedComponent

  override def draw(batch: Batch, parentAlpha: Float): Unit = {
    _batch = batch
    if (component.clip.enabled.get && getWidth > 0.0f && getHeight > 0.0f) {
      import EnhancedActor._
      val x = getX + component.clip.left.get.toFloat
      val y = getY + component.clip.bottom.get.toFloat
      val width = getWidth - component.clip.right.get.toFloat - component.clip.left.get.toFloat
      val height = getHeight - component.clip.top.get.toFloat - component.clip.bottom.get.toFloat
      if (width >= 1.0f && height >= 1.0) {
        clipBounds.set(x, y, width, height)
        ScissorStack.calculateScissors(getStage.getCamera, batch.getTransformMatrix, clipBounds, scissors)
        batch.flush()
        ScissorStack.pushScissors(scissors)
        try {
          super.draw(batch, parentAlpha)
          batch.flush()
        } finally {
          ScissorStack.popScissors()
        }
      }
    } else {
      super.draw(batch, parentAlpha)
    }
    component match {
      case c: ActorIntegrated => {
        val color = getColor
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha)
        c.render.exec()
      }
      case _ => // Not ActorIntegrated
    }
    _batch = null
  }

  override def act(delta: Float): Unit = {
    super.act(delta)
    component match {
      case c: ActorIntegrated => c.act.exec()
      case _ => // Not ActorIntegrated
    }
  }
}

object EnhancedActor {
  private lazy val scissors = new Rectangle
  private lazy val clipBounds = new Rectangle
}