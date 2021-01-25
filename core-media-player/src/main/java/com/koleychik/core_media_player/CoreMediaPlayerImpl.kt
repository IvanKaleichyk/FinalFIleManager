package com.koleychik.core_media_player

import android.content.Context
import android.media.MediaPlayer
import android.os.PowerManager
import android.util.Log
import com.koleychik.models.fileCarcass.MusicModel

class CoreMediaPlayerImpl private constructor(
    private val context: Context,
    private val mediaPlayer: MediaPlayer
) :
    CoreMediaPlayer {

    private val model: MusicModel? = null
    private val TAG = "MAIN_APP_TAG"

    init {
        mediaPlayer.setWakeMode(context, PowerManager.PARTIAL_WAKE_LOCK)
        mediaPlayer.isLooping = false
        Log.e(TAG, "if have error Check permission")
    }

//    companion object {
//        @Volatile
//        private var instance: CoreMediaPlayerImpl? = null
//
//        fun get(mediaPlayer: MediaPlayer = MediaPlayer()): CoreMediaPlayer {
//            if (instance == null)
//                synchronized(CoreMediaPlayer::class.java) {
//                    if (instance == null) instance = CoreMediaPlayerImpl(mediaPlayer)
//                }
//            return instance!!
//        }
//    }

    override fun getMusicModel(): MusicModel? = model

    override fun start() {
        mediaPlayer.apply {
            prepare()
            setOnPreparedListener {
                mediaPlayer.start()
            }
        }
    }

    override fun setMusic(model: MusicModel) {
        mediaPlayer.apply {
            release()
            setDataSource(context, model.uri)
        }
        Log.d(TAG, "media cannot work because path from uri may not be suitable")
    }

    override fun setPosition(nowPosition: Int) {
        mediaPlayer.seekTo(nowPosition)
    }

    override fun addOnCompletionListener(onCompletionListener: MediaPlayer.OnCompletionListener?) {
        mediaPlayer.setOnCompletionListener(onCompletionListener)
    }

    override fun pause() {
        mediaPlayer.pause()
    }

    override fun stop() {
        mediaPlayer.stop()
    }

    override fun reset() {
        stop()
        mediaPlayer.release()
    }
}