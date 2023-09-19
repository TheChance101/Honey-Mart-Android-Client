package org.the_chance.honeymart.data.source.remote.network

object ServiceLocator {
    private var fcmNotification: FCMNotification? = null

    fun initialize(notificationListener: FCMNotification) {
        this.fcmNotification = notificationListener
    }

    fun getFCMNotification(): FCMNotification {
        return fcmNotification ?: throw IllegalStateException("ServiceLocator not initialized")
    }
}
