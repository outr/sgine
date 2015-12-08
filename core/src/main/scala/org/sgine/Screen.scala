package org.sgine

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.{Gdx, Screen => GDXScreen}
import org.sgine.component.{ActorComponent, Component, Container}

class Screen extends RenderFlow with Container {
  private[sgine] lazy val stage = new Stage(new ScreenViewport)
  private[sgine] lazy val gdx = new GDXScreenInstance(this)

  implicit def thisScreen: Screen = this

  resize.on {
    stage.getViewport.update(Gdx.graphics.getWidth, Gdx.graphics.getHeight, true)
    stage.getRoot.setWidth(Gdx.graphics.getWidth)
    stage.getRoot.setHeight(Gdx.graphics.getHeight)
  }

  render.on {
    stage.act(Gdx.graphics.getDeltaTime)
    stage.draw()
  }

  dispose.on {
    stage.dispose()
  }

  override def screen: Screen = this

  override protected def add(child: Component): Unit = {
    super.add(child)

    child match {
      case ac: ActorComponent[_] => screen.stage.getRoot.addActor(ac.actor)
      case _ => // Ignore non-actor components
    }
  }

  override protected def remove(child: Component): Unit = {
    super.remove(child)

    child match {
      case ac: ActorComponent[_] => screen.stage.getRoot.removeActor(ac.actor)
      case _ => // Ignore non-actor components
    }
  }
}

class GDXScreenInstance(screen: Screen) extends GDXScreen {
  override def show(): Unit = {
    if (screen.create.nonEmpty) {
      screen.create.exec()
      screen.create.clear()
    }
    screen.show.exec()
  }

  override def hide(): Unit = screen.hide.exec()

  override def render(delta: Float): Unit = screen.render.exec()

  override def resize(width: Int, height: Int): Unit = screen.resize.exec()

  override def pause(): Unit = screen.pause.exec()

  override def resume(): Unit = screen.resume.exec()

  override def dispose(): Unit = screen.dispose.exec()
}