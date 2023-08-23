package org.the_chance.honeymart.ui.feature.notifications

interface NotificationsInteractionListener {

    fun getAllNotifications()
    fun onGetAllNotifications()
    fun onGetOrderNotifications()
    fun onGetDeliveryNotifications()
}