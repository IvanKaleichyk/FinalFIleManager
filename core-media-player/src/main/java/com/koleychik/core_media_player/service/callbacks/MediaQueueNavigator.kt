package com.koleychik.core_media_player.service.callbacks

import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.session.MediaSessionCompat
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ext.mediasession.TimelineQueueNavigator
import com.koleychik.core_media_player.service.MediaSource

class MediaQueueNavigator(mediaSession: MediaSessionCompat, private val source: MediaSource) :
    TimelineQueueNavigator(mediaSession) {
    override fun getMediaDescription(player: Player, windowIndex: Int): MediaDescriptionCompat =
        source.list[windowIndex].description
}