package org.the_chance.honeymart.data.source.remote.network

object ServiceLocator {
    private var notificationListener: FCMNotification? = null

    fun initialize(notificationListener: FCMNotification) {
        this.notificationListener = notificationListener
    }

    fun getFCMNotification(): FCMNotification {
        return notificationListener ?: throw IllegalStateException("ServiceLocator not initialized")
    }
}
