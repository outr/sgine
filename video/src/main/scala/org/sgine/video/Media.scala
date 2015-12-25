package org.sgine.video

import java.util.concurrent.ConcurrentLinkedQueue

import com.sun.jna.Memory
import uk.co.caprica.vlcj.player.direct.format.RV32BufferFormat
import uk.co.caprica.vlcj.player.direct.{BufferFormat, BufferFormatCallback, DirectMediaPlayer, RenderCallback}
import uk.co.caprica.vlcj.player.{MediaPlayer => VLCMediaPlayer, _}

import scala.collection.JavaConversions._

case class Media(length: Long,
                 videoTracks: List[VideoTrack],
                 audioTracks: List[AudioTrack],
                 textTracks: List[TextTrack])

object Media {
  lazy val factory = new MediaPlayerFactory("--no-video-title-show")
  private lazy val players = new ConcurrentLinkedQueue[VLCMediaPlayer]()

  def parse(resource: String, player: VLCMediaPlayer): Media = {
    var media: Option[Media] = None
    val listener = new MediaPlayerEventAdapter {
      override def mediaMetaChanged(mediaPlayer: VLCMediaPlayer, metaType: Int) = {
        media = apply(player)
      }
    }
    try {
      val muted = player.isMute
      player.mute()
      player.addMediaPlayerEventListener(listener)
      player.prepareMedia(resource)
      player.parseMedia()
      player.play()

      while(media.isEmpty) {
        Thread.sleep(10)
      }
      player.stop()
      player.mute(muted)

      media.get
    } finally {
      player.removeMediaPlayerEventListener(listener)
    }
  }

  def apply(resource: String): Media = {
    val player = Option(players.poll()) match {
      case Some(p) => p
      case None => factory.newDirectMediaPlayer(new BufferFormatCallback {
        override def getBufferFormat(sourceWidth: Int, sourceHeight: Int): BufferFormat = new RV32BufferFormat(1, 1)
      }, new RenderCallback {
        override def display(mediaPlayer: DirectMediaPlayer, nativeBuffers: Array[Memory], bufferFormat: BufferFormat): Unit = {}
      })
    }
    try {
      parse(resource, player)
    } finally {
      players.add(player)
    }
  }

  def apply(player: VLCMediaPlayer): Option[Media] = {
    val meta = player.getMediaMeta
    val length = meta.getLength
    val videoTracks = player.getTrackInfo(TrackType.VIDEO).toList.map(t => VideoTrack(t.asInstanceOf[VideoTrackInfo]))
    val audioTracks = player.getTrackInfo(TrackType.AUDIO).toList.map(t => AudioTrack(t.asInstanceOf[AudioTrackInfo]))
    val textTracks = player.getTrackInfo(TrackType.TEXT).toList.map(t => TextTrack(t.asInstanceOf[TextTrackInfo]))
    if (videoTracks.nonEmpty || audioTracks.nonEmpty || textTracks.nonEmpty) {
      Some(Media(length, videoTracks, audioTracks, textTracks))
    } else {
      None
    }
  }

  def clear(): Unit = {
    players.foreach(_.release())
    players.clear()
  }
}

trait Track {
  def id: Int
  def bitRate: Int
  def codec: Int
  def codecDescription: String
  def codecName: String
  def description: String
  def language: String
  def level: Int
  def originalCodecName: String
  def profile: Int
}

case class VideoTrack(id: Int,
                      bitRate: Int,
                      codec: Int,
                      codecDescription: String,
                      codecName: String,
                      description: String,
                      language: String,
                      level: Int,
                      originalCodecName: String,
                      profile: Int,
                      frameRate: Int,
                      frameRateBase: Int,
                      width: Int,
                      height: Int,
                      aspectRatio: Int,
                      aspectRatioBase: Int) extends Track

object VideoTrack {
  def apply(info: VideoTrackInfo): VideoTrack = {
    VideoTrack(
      info.id(),
      info.bitRate(),
      info.codec(),
      info.codecDescription(),
      info.codecName(),
      info.description(),
      info.language(),
      info.level(),
      info.originalCodecName(),
      info.profile(),
      info.frameRate(),
      info.frameRateBase(),
      info.width(),
      info.height(),
      info.sampleAspectRatio(),
      info.sampleAspectRatioBase()
    )
  }
}

case class AudioTrack(id: Int,
                      bitRate: Int,
                      codec: Int,
                      codecDescription: String,
                      codecName: String,
                      description: String,
                      language: String,
                      level: Int,
                      originalCodecName: String,
                      profile: Int,
                      channel: Int,
                      rate: Int)

object AudioTrack {
  def apply(info: AudioTrackInfo): AudioTrack = {
    AudioTrack(
      info.id(),
      info.bitRate(),
      info.codec(),
      info.codecDescription(),
      info.codecName(),
      info.description(),
      info.language(),
      info.level(),
      info.originalCodecName(),
      info.profile(),
      info.channels(),
      info.rate()
    )
  }
}

case class TextTrack(id: Int,
                     bitRate: Int,
                     codec: Int,
                     codecDescription: String,
                     codecName: String,
                     description: String,
                     language: String,
                     level: Int,
                     originalCodecName: String,
                     profile: Int,
                     encoding: String)

object TextTrack {
  def apply(info: TextTrackInfo): TextTrack = {
    TextTrack(
      info.id(),
      info.bitRate(),
      info.codec(),
      info.codecDescription(),
      info.codecName(),
      info.description(),
      info.language(),
      info.level(),
      info.originalCodecName(),
      info.profile(),
      info.encoding()
    )
  }
}