package org.the_chance.honeymart.ui.features.notifications

import org.the_chance.honeymart.domain.model.Notification
import org.the_chance.honeymart.ui.features.orders.OrderUiState

interface NotificationsInteractionListener {
    fun getAllNotifications(notificationStates: Int)
    fun onCLickNotificationCard(orderDetails: OrderUiState,notification: NotificationUiState)
}