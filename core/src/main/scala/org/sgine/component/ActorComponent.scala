package org.sgine.component

import com.badlogic.gdx.scenes.scene2d.{Actor, Touchable}
import com.badlogic.gdx.utils.Align
import org.sgine.{Color, Screen}
import org.sgine.task.TaskSupport
import reactify.{Channel, Val, Var}

import scala.annotation.tailrec

trait ActorComponent[A <: Actor] extends DimensionedComponent with TaskSupport {
  val color: Var[Color] = Var(Color.White)

  lazy val parentGroup: Val[Option[GroupContainer]] = Val(findParentGroup(parent))
  lazy val render: Channel[Double] = Channel[Double]

  lazy val validateDimensions: Var[Boolean] = Var(true)

  render.on {
    if (validateDimensions()) {
      validateDimensions @= false

      val screen = screenOption().getOrElse(throw new RuntimeException("Screen not found"))
      updateDimensions(screen)
    }
  }

  protected def updateDimensions(screen: Screen): Unit = {
    val y = (-this.y.toFloat + screen.height - height).toFloat
    actor.setX(x.toFloat)
    actor.setY(y)
    actor.setZIndex(z())
    actor.setWidth(width.toFloat)
    actor.setHeight(height.toFloat)
    actor.setRotation(-rotation.toFloat)
    actor.setOrigin(Align.center)
    actor.setScaleX(scaleX.toFloat)
    actor.setScaleY(scaleY.toFloat)
    actor.setColor(color.gdx)
    val touchable = if (visible && isInstanceOf[InteractiveComponent]) {
      Touchable.enabled
    } else {
      Touchable.disabled
    }
    actor.setTouchable(touchable)
  }

  x.and(y).and(width).and(height).and(rotation).and(scaleX).and(scaleY).on(validateDimensions @= true)
  z.on(validateDimensions @= true)
  color.on(validateDimensions @= true)

  parentGroup.changes {
    case (oldValue, newValue) =>
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
