package com.koleychik.core_media_player.di.internal

import android.support.v4.media.session.MediaSessionCompat
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.koleychik.core_media_player.service.callbacks.CustomMediaSessionCallback
import com.koleychik.core_media_player.service.callbacks.CustomNotificationListener
import com.koleychik.core_media_player.service.callbacks.CustomPlayerListener
import dagger.Binds
import dagger.Module

@Module
abstract class CallbackModule {

    @Binds
    abstract fun provideMediaSessionCallback(callback: CustomMediaSessionCallback): MediaSessionCompat.Callback

    @Binds
    abstract fun provideNotificationListener(service: CustomNotificationListener): PlayerNotificationManager.NotificationListener

    @Binds
    abstract fun providePlayerListener(listener: CustomPlayerListener): Player.EventListener

}