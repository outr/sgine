package org.sgine

import com.badlogic.gdx.ApplicationListener

class GDXApplicationListener(ui: UI) extends ApplicationListener {
  override def create(): Unit = ui.create.exec()

  override def resize(width: Int, height: Int): Unit = ui.resize.exec()

  override def render(): Unit = ui.render.exec()

  override def pause(): Unit = ui.pause.exec()

  override def resume(): Unit = ui.resume.exec()

  override def dispose(): Unit = ui.dispose.exec()
}