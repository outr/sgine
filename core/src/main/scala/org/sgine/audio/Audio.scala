package org.sgine.audio

import com.badlogic.gdx.Gdx
import org.sgine.update.UpdateSupport

object Audio extends UpdateSupport {
  private var entries: List[AudioElement] = Nil

  private def add(entry: AudioElement): Unit = synchronized {
    entries = entry :: entries
  }

  private[audio] def remove(entry: AudioElement): Unit = synchronized {
    entries = entries.filterNot(_ eq entry)
  }

  def sound(path: String): Sound = {
    val gdxSound = Gdx.audio.newSound(Gdx.files.internal(path))
    val sound = new Sound(gdxSound)
    add(sound)
    sound
  }

  def music(path: String): Music = {
    val gdxMusic = Gdx.audio.newMusic(Gdx.files.internal(path))
    val music = new Music(gdxMusic)
    add(music)
    music
  }

  override def update(delta: Double): Unit = {
    super.update(delta)

    entries.foreach(_.update(delta))
  }
}