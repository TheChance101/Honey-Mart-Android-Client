package org.the_chance.honeymart.di

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import org.the_chance.honeymart.data.source.remote.network.FCMNotificationClickListener
import org.the_chance.honeymart.ui.MainActivity
import javax.inject.Inject

class FCMNotificationClickListenerImp @Inject constructor(
    val context: Context
) : FCMNotificationClickListener {
    override fun onNotificationClicked(): PendingIntent {

        val intent = Intent(context, MainActivity::class.java)
        intent.apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            action = "OPEN_NOTIFICATIONS_SCREEN"
        }

        return PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )
    }
}
