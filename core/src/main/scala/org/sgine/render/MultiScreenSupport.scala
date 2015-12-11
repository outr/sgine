package org.sgine.render

import com.badlogic.gdx.Gdx
import org.sgine.{Screen, UI}

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer

trait MultiScreenSupport extends UI {
  val activeScreens = new ActiveScreens(this)

  private val renderFunction = (screen: Screen) => screen.render.exec()

  render.on {
    withScreens(renderFunction, activeScreens.toList)
  }
  resize.on {
    withScreens(screen => screen.gdx.resize(Gdx.graphics.getWidth, Gdx.graphics.getHeight), activeScreens.toList)
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
    screens -= screen
    screens += screen
    screen.gdx.show()
    screen.gdx.resize(Gdx.graphics.getWidth, Gdx.graphics.getHeight)
  }

  def insert(index: Int, screen: Screen) = ui.render.once {
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
    this.screens.foreach(remove)
    screens.foreach(add)
  }

  def clear() = ui.render.once {
    screens.foreach(_.gdx.dispose())
    screens.clear()
  }

  override def iterator: Iterator[Screen] = screens.iterator

  override def toList: List[Screen] = screens.toList
}