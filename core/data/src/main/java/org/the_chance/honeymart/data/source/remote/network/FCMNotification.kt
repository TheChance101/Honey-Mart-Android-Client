package org.the_chance.honeymart.data.source.remote.network

import android.app.PendingIntent

interface FCMNotification {
    fun getClickPendingIntent(): PendingIntent
}
