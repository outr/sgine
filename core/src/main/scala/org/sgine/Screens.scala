package org.sgine

import org.sgine.update.Updatable
import reactify.{Channel, Var}

class Screens extends Var[List[Screen]] with Updatable { self =>
  val added: Channel[Screen] = Channel[Screen]
  val removed: Channel[Screen] = Channel[Screen]

  changes {
    case (oldValue, newValue) =>
      val rem = oldValue.diff(newValue)
      val add = newValue.diff(oldValue)
      rem.foreach(s => removed @= s)
      add.foreach(s => added @= s)
  }

  set(Nil)

  def add(screens: Screen*): Unit = {
    this @= get ::: screens.toList
  }

  def +=(screen: Screen): Unit = add(screen)
  def -=(screen: Screen): Unit = remove(screen)

  def remove(screens: Screen*): Unit = {
    val r = screens.toSet
    this @= get.filterNot(r.contains)
  }

  def clear(): Unit = this @= Nil

  override def update(delta: Double): Unit = get.foreach(_.update(delta))
}