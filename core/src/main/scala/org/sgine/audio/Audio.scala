package org.sgine.audio

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import fabric.io.{JsonFormatter, JsonParser}
import fabric.rw._
import fabric._
import org.matthicks.media4s.video.VideoUtil
import org.sgine.update.UpdateSupport
import reactify.Var

import java.io.{BufferedWriter, File, FileWriter}
import java.nio.file.Files

object Audio extends UpdateSupport {
  private var entries: List[AudioElement] = Nil

  private lazy val infoFile = Gdx.files.local("audio.info.json")
  private lazy val _info: Var[Map[String, AudioInfo]] = {
    val map: Map[String, AudioInfo] = if (infoFile.exists()) {
      val jsonString = infoFile.readString()
      val json = JsonParser(jsonString)
      json.as[Map[String, AudioInfo]]
    } else {
      Map.empty
    }
    val v = Var(map)
    v.attach { map =>
      val json = map.asJson
      val jsonString = JsonFormatter.Default(json)
      infoFile.writeString(jsonString, false)
    }
    v
  }

  def info(file: FileHandle): AudioInfo = _info().get(file.path()) match {
    case Some(info) => info
    case None =>
      val tmp = File.createTempFile("audio-info", file.path())
      val bytes = file.readBytes()
      Files.write(tmp.toPath, bytes)

      val mediaInfo = VideoUtil.info(tmp)
      val info = AudioInfo(mediaInfo.duration)
      synchronized {
        _info @= _info() + (file.path() -> info)
      }
      info
  }

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
    val file = Gdx.files.internal(path)
    val gdxMusic = Gdx.audio.newMusic(file)
    val info = this.info(file)
    val music = new Music(gdxMusic, info)
    add(music)
    music
  }

  // TODO: player - allows chaining music with transition effects and repeats

  override def update(delta: Double): Unit = {
    super.update(delta)

    entries.foreach(_.update(delta))
  }
}