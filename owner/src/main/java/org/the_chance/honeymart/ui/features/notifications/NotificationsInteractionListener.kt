package org.the_chance.honeymart.ui.features.notifications

import org.the_chance.honeymart.ui.features.orders.OrderUiState

interface NotificationsInteractionListener {
    fun getAllNotifications(state: Int,notificationStates: NotificationStates)
    fun onCLickNotificationCard(orderDetails: OrderUiState,notification: NotificationUiState)
}