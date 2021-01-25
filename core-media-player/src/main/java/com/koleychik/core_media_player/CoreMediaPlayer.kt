package com.koleychik.core_media_player

import android.media.MediaPlayer
import com.koleychik.models.fileCarcass.MusicModel

interface CoreMediaPlayer {
    fun getMusicModel(): MusicModel?
    fun start()
    fun setMusic(model: MusicModel)
    fun setPosition(nowPosition: Int)
    fun pause()
    fun addOnCompletionListener(onCompletionListener: MediaPlayer.OnCompletionListener?)
    fun stop()
    fun reset()
}