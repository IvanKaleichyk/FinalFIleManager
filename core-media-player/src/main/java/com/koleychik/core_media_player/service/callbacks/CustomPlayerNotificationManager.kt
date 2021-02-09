package com.koleychik.core_media_player.service.callbacks

import android.content.Context
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.koleychik.core_media_player.R

class CustomPlayerNotificationManager(
    context: Context,
    token: MediaSessionCompat.Token,
    notificationListener: PlayerNotificationManager.NotificationListener,
    descriptionAdapter: PlayerNotificationManager.MediaDescriptionAdapter
) {

    private val notificationManager: PlayerNotificationManager


    companion object {
        const val NOW_PLAYING_NOTIFICATION_ID = 10101
        const val NOW_PLAYING_CHANNEL_ID = "101001"
    }

    init {
        val mediaController = MediaControllerCompat(context, token)

        notificationManager = PlayerNotificationManager.createWithNotificationChannel(
            context,
            NOW_PLAYING_CHANNEL_ID,
            R.string.notification_channel,
            R.string.notification_channel_description,
            NOW_PLAYING_NOTIFICATION_ID,
            descriptionAdapter,
            notificationListener
        ).apply {

            setMediaSessionToken(token)
//            setSmallIcon(R.drawable.ic_notification)

            // Don't display the rewind or fast-forward buttons.
            setRewindIncrementMs(0)
            setFastForwardIncrementMs(0)
        }
    }

    fun hideNotification() {
        notificationManager.setPlayer(null)
    }

    fun showNotificationForPlayer(player: Player) {
        notificationManager.setPlayer(player)
    }

}