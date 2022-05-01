package org.sgine

import org.sgine.easing.Easing
import org.sgine.task._
import org.sgine.update.Updatable
import reactify._
import scribe.Execution.global

import scala.concurrent.Future
import scala.concurrent.duration.{DurationInt, FiniteDuration}

class Screens extends Var[List[Screen]] with Updatable { self =>
  val added: Channel[Screen] = Channel[Screen]
  val removed: Channel[Screen] = Channel[Screen]

  lazy val unlocked: Val[List[Screen]] = Val(get.filterNot(_.locked))
  lazy val locked: Val[List[Screen]] = Val(get.filter(_.locked))

  changes {
    case (oldValue, newValue) =>
      val rem = oldValue.diff(newValue)
      val add = newValue.diff(oldValue)
      rem.foreach(s => removed @= s)
      add.foreach(s => added @= s)
  }
  removed.attach { screen =>
    if (screen.locked()) {
      scribe.debug(s"Attempted to remove locked screen: $screen. Adding back...")
      add(screen)
    }
  }
  added.on {    // Make sure we're sorted by priority
    val list = get
    val sorted = list.sortBy(_.priority())
    if (sorted != list) {
      static(sorted)
    }
  }

  set(Nil)
  set(List(Overlay))

  def add(screens: Screen*): Unit = {
    this @= get ::: screens.toList
  }

  def +=(screen: Screen): Unit = add(screen)
  def -=(screen: Screen): Unit = remove(screen)

  def remove(screens: Screen*): Unit = {
    val r = screens.toSet
    this @= get.filterNot(r.contains)
  }

  object transition {
    private var active = Future.successful(())

    def to(screen: Screen)(effect: => Task)(implicit taskSupport: TaskSupport): TaskInstance = synchronized {
      val current = unlocked()
      add(screen)
      val instance = sequential(
        active,
        effect,
        synchronous(remove(current: _*))
      ).start
      active = instance.future.map(_ => ())
      instance
    }

    def slideLeft(screen: Screen, duration: FiniteDuration = 1.second, easing: Easing = Easing.linear)
                 (implicit taskSupport: TaskSupport): TaskInstance = {
      val existing = unlocked().map { s =>
        s.right to 0.0 in duration easing easing
      }
      screen.left @= screen.width
      to(screen) {
        parallel(
          (screen.left to 0.0 in duration easing easing) :: existing: _*
        )
      }
    }

    def slideRight(screen: Screen, duration: FiniteDuration = 1.second, easing: Easing = Easing.linear)
                 (implicit taskSupport: TaskSupport): TaskInstance = {
      val existing = unlocked().map { s =>
        s.left to s.width in duration easing easing
      }
      screen.right @= 0.0
      to(screen) {
        parallel(
          (screen.left to 0.0 in duration easing easing) :: existing: _*
        )
      }
    }

    def slideUp(screen: Screen, duration: FiniteDuration = 1.second, easing: Easing = Easing.linear)
                  (implicit taskSupport: TaskSupport): TaskInstance = {
      val existing = unlocked().map { s =>
        s.bottom to 0.0 in duration easing easing
      }
      screen.top @= screen.height
      to(screen) {
        parallel(
          (screen.top to 0.0 in duration easing easing) :: existing: _*
        )
      }
    }

    def slideDown(screen: Screen, duration: FiniteDuration = 1.second, easing: Easing = Easing.linear)
               (implicit taskSupport: TaskSupport): TaskInstance = {
      val existing = unlocked().map { s =>
        s.top to s.height in duration easing easing
      }
      screen.bottom @= 0.0
      to(screen) {
        parallel(
          (screen.top to 0.0 in duration easing easing) :: existing: _*
        )
      }
    }
  }

  def clear(): Unit = this @= get.filterNot(_.locked())

  override def update(delta: Double): Unit = get.foreach(_.update(delta))
}