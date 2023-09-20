package org.the_chance.honeymart.ui.feature.notifications

interface NotificationsInteractionListener {
    fun getAllNotifications()
    fun onGetAllNotifications()
    fun onGetProcessingNotifications()
    fun onGetCompletedNotifications()
    fun onClickTryAgain()
    fun onClickDiscoverMarket()
}