package org.sgine.video

import uk.co.caprica.vlcj.binding.internal.libvlc_media_t
import uk.co.caprica.vlcj.player
import uk.co.caprica.vlcj.player.MediaPlayerEventListener

class PlayerListener(mp: MediaPlayer) extends MediaPlayerEventListener {
  override def opening(mediaPlayer: player.MediaPlayer): Unit = mp._state := PlayerState.Opening

  override def pausableChanged(mediaPlayer: player.MediaPlayer, newPausable: Int): Unit = {}

  override def subItemFinished(mediaPlayer: player.MediaPlayer, subItemIndex: Int): Unit = {}

  override def chapterChanged(mediaPlayer: player.MediaPlayer, newChapter: Int): Unit = {}

  override def scrambledChanged(mediaPlayer: player.MediaPlayer, newScrambled: Int): Unit = {}

  override def mediaDurationChanged(mediaPlayer: player.MediaPlayer, newDuration: Long): Unit = {}

  override def elementaryStreamAdded(mediaPlayer: player.MediaPlayer, `type`: Int, id: Int): Unit = {}

  override def stopped(mediaPlayer: player.MediaPlayer): Unit = mp._state := PlayerState.Stopped

  override def mediaParsedChanged(mediaPlayer: player.MediaPlayer, newStatus: Int): Unit = {}

  override def volumeChanged(mediaPlayer: player.MediaPlayer, volume: Float): Unit = {}

  override def mediaSubItemAdded(mediaPlayer: player.MediaPlayer, subItem: libvlc_media_t): Unit = {}

  override def mediaMetaChanged(mediaPlayer: player.MediaPlayer, metaType: Int): Unit = {}

  override def titleChanged(mediaPlayer: player.MediaPlayer, newTitle: Int): Unit = {}

  override def playing(mediaPlayer: player.MediaPlayer): Unit = mp._state := PlayerState.Playing

  override def subItemPlayed(mediaPlayer: player.MediaPlayer, subItemIndex: Int): Unit = {}

  override def error(mediaPlayer: player.MediaPlayer): Unit = mp._state := PlayerState.Error

  override def finished(mediaPlayer: player.MediaPlayer): Unit = mp._state := PlayerState.Finished

  override def mediaFreed(mediaPlayer: player.MediaPlayer): Unit = mp._state := PlayerState.Freed

  override def paused(mediaPlayer: player.MediaPlayer): Unit = mp._state := PlayerState.Paused

  override def snapshotTaken(mediaPlayer: player.MediaPlayer, filename: String): Unit = {}

  override def audioDeviceChanged(mediaPlayer: player.MediaPlayer, audioDevice: String): Unit = {}

  override def positionChanged(mediaPlayer: player.MediaPlayer, newPosition: Float): Unit = {
    mp.status._position := newPosition.toDouble
  }

  override def seekableChanged(mediaPlayer: player.MediaPlayer, newSeekable: Int): Unit = {}

  override def muted(mediaPlayer: player.MediaPlayer, muted: Boolean): Unit = {}

  override def endOfSubItems(mediaPlayer: player.MediaPlayer): Unit = {}

  override def forward(mediaPlayer: player.MediaPlayer): Unit = {}

  override def elementaryStreamDeleted(mediaPlayer: player.MediaPlayer, `type`: Int, id: Int): Unit = {}

  override def mediaSubItemTreeAdded(mediaPlayer: player.MediaPlayer, item: libvlc_media_t): Unit = {}

  override def buffering(mediaPlayer: player.MediaPlayer, newCache: Float): Unit = {}

  override def lengthChanged(mediaPlayer: player.MediaPlayer, newLength: Long): Unit = {
    mp._media := mp._media.get.map(_.copy(length = newLength))
  }

  override def mediaChanged(mediaPlayer: player.MediaPlayer, media: libvlc_media_t, mrl: String): Unit = {}

  override def elementaryStreamSelected(mediaPlayer: player.MediaPlayer, `type`: Int, id: Int): Unit = {}

  override def backward(mediaPlayer: player.MediaPlayer): Unit = {}

  override def videoOutput(mediaPlayer: player.MediaPlayer, newCount: Int): Unit = {}

  override def mediaStateChanged(mediaPlayer: player.MediaPlayer, newState: Int): Unit = {}

  override def timeChanged(mediaPlayer: player.MediaPlayer, newTime: Long): Unit = {
    mp.status._time := newTime / 1000.0
  }

  override def newMedia(mediaPlayer: player.MediaPlayer): Unit = {}

  override def corked(mediaPlayer: player.MediaPlayer, corked: Boolean): Unit = {}
}
