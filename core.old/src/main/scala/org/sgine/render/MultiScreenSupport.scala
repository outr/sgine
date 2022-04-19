package org.sgine.render

import com.badlogic.gdx.Gdx
import org.sgine.{Screen, UI}

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer

trait MultiScreenSupport extends UI {
  override def screen: Screen = activeScreens.last

  private[render] var _screens = Set.empty[Screen]
  def screens: Set[Screen] = _screens

  val activeScreens = new ActiveScreens(this)

  private val renderFunction = (screen: Screen) => screen.render.exec()

  render.on {
    withScreens(renderFunction, activeScreens.toList)
  }
  resize.on {
    withScreens(screen => screen.gdx.resize(Gdx.graphics.getWidth, Gdx.graphics.getHeight), activeScreens.toList)
  }
  dispose.on {
    screens.foreach(_.dispose.exec())
  }

  @tailrec
  final def withScreens(f: Screen => Unit, screens: List[Screen]): Unit = {
    if (screens.nonEmpty) {
      f(screens.head)
      withScreens(f, screens.tail)
    }
  }
}

class ActiveScreens(ui: MultiScreenSupport) extends Iterable[Screen] {
  private val screens = ListBuffer.empty[Screen]

  def add(screen: Screen) = ui.render.once {
    ui._screens += screen
    screens -= screen
    screens += screen
    screen.gdx.show()
    screen.gdx.resize(Gdx.graphics.getWidth, Gdx.graphics.getHeight)
  }

  def insert(index: Int, screen: Screen) = ui.render.once {
    ui._screens += screen
    screens -= screen
    screens.insert(index, screen)
    screen.gdx.show()
    screen.gdx.resize(Gdx.graphics.getWidth, Gdx.graphics.getHeight)
  }

  def remove(screen: Screen) = ui.render.once {
    screen.gdx.hide()
    screens -= screen
  }

  def set(screens: Screen*) = ui.render.once {
    ui._screens ++= screens
    this.screens.foreach(remove)
    screens.foreach(add)
  }

  def clear() = ui.render.once {
    screens.foreach(_.gdx.dispose())
    screens.clear()
  }

  override def last: Screen = screens.last

  override def lastOption: Option[Screen] = screens.lastOption

  override def iterator: Iterator[Screen] = screens.iterator

  override def toList: List[Screen] = screens.toList
}