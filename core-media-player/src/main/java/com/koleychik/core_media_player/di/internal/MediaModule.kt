package com.koleychik.core_media_player.di.internal

import android.support.v4.media.session.MediaSessionCompat
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.koleychik.core_media_player.service.MediaConstants.TAG
import com.koleychik.core_media_player.service.MediaPlayerService
import com.koleychik.core_media_player.service.callbacks.CustomPlayerNotificationManager
import dagger.Module
import dagger.Provides

@Module
class MediaModule(private val service: MediaPlayerService, private val packageName: String) {

    @Provides
    fun provideDataSourceFactory() = DefaultDataSourceFactory(
        service.applicationContext,
        Util.getUserAgent(service.applicationContext, packageName)
    )

    @Provides
    fun provideMediaSession() =
        MediaSessionCompat(service.applicationContext, TAG)

    @Provides
    fun provideSessionToken() = service.sessionToken

    @Provides
    fun provideNotificationManager(
        token: MediaSessionCompat.Token,
        descriptionAdapter: PlayerNotificationManager.MediaDescriptionAdapter,
        notificationListener: PlayerNotificationManager.NotificationListener
    ) = CustomPlayerNotificationManager(service.applicationContext, token, notificationListener, descriptionAdapter)

    @Provides
    fun providePlayer(playerListener: Player.EventListener) =
        SimpleExoPlayer.Builder(service.applicationContext).build().apply {
            setAudioAttributes(audioAttributes, true)
            setHandleAudioBecomingNoisy(true)
            addListener(playerListener)
        }

}