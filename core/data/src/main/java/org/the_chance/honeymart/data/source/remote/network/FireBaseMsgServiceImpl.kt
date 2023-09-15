package org.the_chance.honeymart.data.source.remote.network

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.tasks.await
import org.the_chance.honeymart.data.R
import javax.inject.Inject

class FireBaseMsgServiceImpl @Inject constructor(
) : FirebaseMessagingService(), FireBaseMessageService {
    private val firebaseMessaging = FirebaseMessaging.getInstance()
    private val clickPendingIntent = ServiceLocator.getFCMNotification().getClickPendingIntent()

    override fun onNewToken(token: String) {
        Log.d("TAG", "Refreshed token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val notification = remoteMessage.notification
        if (notification != null) {
            val title = notification.title
            val body = notification.body
            showNotification(title, body)
        }
    }

    private fun showNotification(title: String?, message: String?) {
        val channelId = "default_channel"
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.icon_order_nav)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .addAction(0,"Show",clickPendingIntent)

        notificationBuilder.setContentIntent(clickPendingIntent)


        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            channelId,
            "Default Channel",
            NotificationManager.IMPORTANCE_HIGH
        )
        channel.enableLights(true)
        channel.lightColor = Color.RED
        channel.enableVibration(true)
        notificationManager.createNotificationChannel(channel)

        val notificationId = 1
        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    override suspend fun getDeviceToken(): String {
        return firebaseMessaging.token.await()
    }

}