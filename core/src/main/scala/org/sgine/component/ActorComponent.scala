package org.sgine.component

import com.badlogic.gdx.scenes.scene2d.{Actor, Touchable}
import com.badlogic.gdx.utils.Align
import org.sgine.{Color, Screen}
import org.sgine.task.TaskSupport
import reactify.{Channel, Var}

trait ActorComponent[A <: Actor] extends DimensionedComponent with TaskSupport {
  val color: Var[Color] = Var(Color.White)

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
    val parentHeight = parent().map {
      case dc: DimensionedComponent => dc.height()
      case _ => screen.height()
    }.getOrElse(screen.height())
    val y = (-this.y.toFloat + parentHeight - height).toFloat
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

  parent.changes {
    case (oldValue, newValue) =>
      oldValue.foreach {
        case p: TypedContainer[_] => p.actor.removeActor(actor)
      }
      newValue.foreach {
        case p: TypedContainer[_] => p.actor.addActor(actor)
      }
  }

  def actor: A
}
