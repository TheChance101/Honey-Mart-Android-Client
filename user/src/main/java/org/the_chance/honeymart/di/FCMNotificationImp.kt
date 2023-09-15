package org.the_chance.honeymart.di

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import org.the_chance.honeymart.data.source.remote.network.FCMNotification
import org.the_chance.honeymart.ui.MainActivity
import org.the_chance.honeymart.ui.feature.notifications.USER_NOTIFICATION_URI

class FCMNotificationImp (
    val context: Context
) : FCMNotification {
    private val flag = PendingIntent.FLAG_IMMUTABLE

    private val clickIntent =
        Intent(Intent.ACTION_VIEW, USER_NOTIFICATION_URI.toUri(), context, MainActivity::class.java)
    private val clickPendingIntent = TaskStackBuilder.create(context).run {
        addNextIntentWithParentStack(clickIntent)
        getPendingIntent(1,flag)
    }

    override fun getClickPendingIntent(): PendingIntent {
        return clickPendingIntent
    }
}


