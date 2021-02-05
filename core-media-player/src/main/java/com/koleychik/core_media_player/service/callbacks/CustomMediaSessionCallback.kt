package com.koleychik.core_media_player.service.callbacks

import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import com.google.android.exoplayer2.ExoPlayer
import com.koleychik.core_media_player.service.MediaConstants.TAG
import com.koleychik.core_media_player.service.MediaPlayerService

class CustomMediaSessionCallback(
    private val service: MediaPlayerService,
    private val notificationManager: CustomPlayerNotificationManager,
    private val player: ExoPlayer
) : MediaSessionCompat.Callback() {

    override fun onPlay() {
        super.onPlay()
        service.apply {
            prepareToPlay()
            mediaSession.isActive = true

            setMediaSessionState(PlaybackStateCompat.STATE_PLAYING)
        }
        notificationManager.showNotificationForPlayer(service.player)
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "MediaPlaybackService onPause")
        if (player.playWhenReady) player.playWhenReady = false
        setMediaSessionState(PlaybackStateCompat.STATE_PAUSED)
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "MediaPlaybackService onStop")
        setMediaSessionState(PlaybackStateCompat.STATE_STOPPED)

        service.apply {
            stopForeground(true)
            mediaSession.isActive = false
        }

        notificationManager.hideNotification()
        player.apply {
            playWhenReady = false
            stop()
        }
    }

    override fun onSkipToNext() {
        super.onSkipToNext()
        with(service) {
            prepareToPlay(mediaSource.list.indexOf(mediaSource.getNext()))
        }
        setMediaSessionState(PlaybackStateCompat.STATE_SKIPPING_TO_NEXT)
    }

    override fun onSkipToPrevious() {
        super.onSkipToPrevious()
        with(service) {
            prepareToPlay(mediaSource.list.indexOf(mediaSource.getPrevious()))
        }
        setMediaSessionState(PlaybackStateCompat.STATE_SKIPPING_TO_PREVIOUS)
    }

    private fun setMediaSessionState(state: Int) {
        service.mediaSession.setPlaybackState(
            PlaybackStateCompat.Builder().setState(
                state,
                PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN,
                1f
            ).build()
        )
    }

}