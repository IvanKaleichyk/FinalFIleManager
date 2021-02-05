package com.koleychik.core_media_player.service.callbacks

import android.app.Notification
import android.app.Service
import android.content.Intent
import androidx.core.content.ContextCompat
import com.google.android.exoplayer2.ui.PlayerNotificationManager

class CustomNotificationListener(private val service: Service) :
    PlayerNotificationManager.NotificationListener {

    private var isForeground = false

    override fun onNotificationPosted(
        notificationId: Int,
        notification: Notification,
        ongoing: Boolean
    ) {
        super.onNotificationPosted(notificationId, notification, ongoing)
        if (ongoing && !isForeground) {
            isForeground = true
            startService(notificationId, notification)
        }
    }

    private fun startService(notificationId: Int, notification: Notification) {
        ContextCompat.startForegroundService(
            service.applicationContext,
            Intent(service.applicationContext, this.javaClass)
        )
        service.startForeground(notificationId, notification)
    }

    override fun onNotificationCancelled(notificationId: Int, dismissedByUser: Boolean) {
        super.onNotificationCancelled(notificationId, dismissedByUser)
        isForeground = false
        service.stopForeground(true)
        service.stopSelf()

    }
}