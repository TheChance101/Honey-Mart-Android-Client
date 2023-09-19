package org.the_chance.honeymart.di

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import org.the_chance.honeymart.data.source.remote.network.FCMNotification
import org.the_chance.honeymart.ui.MainActivity

class FCMNotificationImp (
    val context: Context
) : FCMNotification {
    private val flag = PendingIntent.FLAG_IMMUTABLE
     val ADMIN_NOTIFICATION_URI = "https://honeymart.admin.ui.feature.markets"

    override fun getClickPendingIntent(): PendingIntent {
        val clickIntent =
            Intent(Intent.ACTION_VIEW, ADMIN_NOTIFICATION_URI.toUri(), context,
                MainActivity::class.java)
        val clickPendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(clickIntent)
            getPendingIntent(1,flag)
        }
        return clickPendingIntent
    }
}
