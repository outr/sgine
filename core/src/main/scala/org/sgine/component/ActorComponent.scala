package org.sgine.component

import com.badlogic.gdx.scenes.scene2d.{Actor, Touchable}
import com.badlogic.gdx.utils.Align
import org.sgine.{Color, Screen}
import org.sgine.task.TaskSupport
import reactify._

import scala.util.Try

trait ActorComponent[A <: Actor] extends DimensionedComponent with TaskSupport {
  val color: Var[Color] = Var(Color.White)

  lazy val render: Channel[Double] = Channel[Double]

  lazy val validateDimensions: Var[Boolean] = Var(true)

  /**
    * Update on render
    */
  protected def uor[T](v: Var[T])(f: T => Unit): Unit = v.attach { t =>
    render.once { _ =>
      f(t)
    }
  }

  render.on {
    if (validateDimensions()) {
      validateDimensions @= false

      Try {
        val screen = screenOption().getOrElse(throw new RuntimeException("Screen not found"))
        updateDimensions(screen)
      }.failed.foreach { t =>
        scribe.error(s"Updating dimensions failed for $this", t)
      }
    }
  }

  uor(rotation) { r =>
    actor.setRotation(-r.toFloat)
  }
  uor(z) { z =>
    actor.setZIndex(z)
  }
  uor(scaleX) { s =>
    actor.setScaleX(s.toFloat)
  }
  uor(scaleY) { s =>
    actor.setScaleY(s.toFloat)
  }
  uor(color) { c =>
    actor.setColor(c.gdx)
  }

  protected def updateDimensions(screen: Screen): Unit = {
    val parentHeight = parent().map {
      case dc: DimensionedComponent => dc.height()
      case _ => screen.height()
    }.getOrElse(screen.height())
    val y = (-this.y.toFloat + parentHeight - height).toFloat
    actor.setX(x.toFloat)
    actor.setY(y)
    actor.setWidth(width.toFloat)
    actor.setHeight(height.toFloat)
    actor.setOrigin(Align.center)
    val touchable = if (visible && isInstanceOf[InteractiveComponent]) {
      Touchable.enabled
    } else {
      Touchable.disabled
    }
    actor.setTouchable(touchable)
  }

  x.and(y).and(width).and(height).on(validateDimensions @= true)

  parent.changes {
    case (oldValue, newValue) =>
      oldValue.foreach {
        case p: TypedContainer[_] => p.actor.removeActor(actor)
      }
      newValue.foreach {
        case p: TypedContainer[_] =>
          p.actor.addActor(actor)
      }
  }

  def actor: A
}
