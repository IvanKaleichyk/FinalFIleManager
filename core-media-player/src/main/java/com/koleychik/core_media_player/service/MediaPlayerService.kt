package com.koleychik.core_media_player.service

import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaSessionCompat
import androidx.media.MediaBrowserServiceCompat
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.koleychik.core_media_player.service.MediaConstants.MEDIA_ROOT_ID
import com.koleychik.core_media_player.service.extensions.asMediaItem
import com.koleychik.core_media_player.service.extensions.asMediaSource
import com.koleychik.core_media_player.service.extensions.createPendingIntent
import javax.inject.Inject
import javax.inject.Named

class MediaPlayerService : MediaBrowserServiceCompat() {

    @Inject
    lateinit var mediaSource: MediaSource

    @Inject
    @Named("MainActivity")
    lateinit var cls: Class<*>

    @Inject
    lateinit var dsf: DefaultDataSourceFactory

    @Inject
    lateinit var mediaSession: MediaSessionCompat

    @Inject
    lateinit var player: SimpleExoPlayer

    @Inject
    lateinit var playerListener: Player.EventListener

    @Inject
    lateinit var mediaSessionCallback: MediaSessionCompat.Callback

    override fun onCreate() {
        super.onCreate()
        setupMediaSession()
    }

    private fun setupMediaSession() {
        mediaSession.apply {
            setSessionActivity(createPendingIntent(cls))
            setSessionToken(sessionToken)
            setCallback(mediaSessionCallback)
        }
    }

    fun prepareToPlay(_currentMetadataPosition: Int? = mediaSource.currentMetadataPosition) {
        val currentMetadata: Int = _currentMetadataPosition ?: 0
        player.apply {
            prepare(mediaSource.list.asMediaSource(dsf))
            seekTo(currentMetadata, 0L)
            playWhenReady = true
        }
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot = BrowserRoot(MEDIA_ROOT_ID, null)

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        if (parentId == MEDIA_ROOT_ID) result.sendResult(mediaSource.list.asMediaItem())
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaSession.release()
        player.apply {
            removeListener(playerListener)
            release()
        }
    }
}