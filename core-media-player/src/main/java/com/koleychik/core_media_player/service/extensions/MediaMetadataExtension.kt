package com.koleychik.core_media_player.service.extensions

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import androidx.core.net.toUri
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource

fun List<MediaMetadataCompat>.asMediaItem(): MutableList<MediaBrowserCompat.MediaItem> {
    val list = mutableListOf<MediaBrowserCompat.MediaItem>()
    forEach { audio ->
        val desc = MediaDescriptionCompat.Builder()
            .setMediaUri(audio.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI).toUri())
            .setTitle(audio.description.title)
            .setSubtitle(audio.description.subtitle)
            .setMediaId(audio.description.mediaId)
            .setIconUri(audio.description.iconUri)
            .build()
        list.add(MediaBrowserCompat.MediaItem(desc, MediaBrowserCompat.MediaItem.FLAG_PLAYABLE))
    }
    return list
}

fun List<MediaMetadataCompat>.asMediaSource(dataSourceFactory: DataSource.Factory): ConcatenatingMediaSource {
    val concatenatingMediaSource = ConcatenatingMediaSource()
    forEach {
        concatenatingMediaSource.addMediaSource(it.toMediaSource(dataSourceFactory))
    }
    return concatenatingMediaSource
}

fun MediaMetadataCompat.toMediaSource(dataSourceFactory: DataSource.Factory): ProgressiveMediaSource =
    ProgressiveMediaSource.Factory(dataSourceFactory)
        .createMediaSource(getString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI).toUri())