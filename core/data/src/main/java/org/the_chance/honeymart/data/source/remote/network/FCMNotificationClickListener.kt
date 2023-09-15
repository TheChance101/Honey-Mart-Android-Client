package org.the_chance.honeymart.data.source.remote.network

import android.app.PendingIntent

interface FCMNotificationClickListener {
    fun onNotificationClicked(): PendingIntent
}
