package com.koleychik.core_media_player.service.extensions

import android.app.PendingIntent
import android.app.Service
import android.content.Intent

fun Service.createPendingIntent(cls : Class<*>): PendingIntent = PendingIntent.getActivity(
    applicationContext,
    0,
    Intent(applicationContext, cls),
    PendingIntent.FLAG_CANCEL_CURRENT
)