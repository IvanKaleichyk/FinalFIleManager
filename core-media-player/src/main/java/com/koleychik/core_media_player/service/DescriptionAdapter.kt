package com.koleychik.core_media_player.service

import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager

class DescriptionAdapter(
    private val context: Context,
    private val mediaSession: MediaSessionCompat
) :
    PlayerNotificationManager.MediaDescriptionAdapter {

    private val mediaController = mediaSession.controller

    private fun onClick(){
        mediaSession.
    }

    override fun getCurrentContentTitle(player: Player): CharSequence {
        onClick(player.currentWindowIndex)
        return mediaController.metadata.description.title.toString()
    }

    override fun createCurrentContentIntent(player: Player): PendingIntent =
        mediaController.sessionActivity

    override fun getCurrentContentText(player: Player): CharSequence =
        mediaController.metadata.description.title.toString()

    override fun getCurrentLargeIcon(
        player: Player,
        callback: PlayerNotificationManager.BitmapCallback
    ): Bitmap? {
        Glide.with(context).asBitmap()
            .load(mediaController.metadata.description.iconUri)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    callback.onBitmap(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) = Unit
            })
        return null
    }
}