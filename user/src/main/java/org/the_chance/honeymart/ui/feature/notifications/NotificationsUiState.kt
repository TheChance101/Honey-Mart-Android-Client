package org.the_chance.honeymart.ui.feature.notifications

import org.the_chance.honeymart.domain.model.NotificationEntity
import org.the_chance.honeymart.domain.util.ErrorHandler

data class NotificationsUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: ErrorHandler? = null,
    val notificationState: NotificationStates = NotificationStates.ALL,
    val updatedNotifications: List<Notification> = emptyList(),
    val notifications: List<Notification> = emptyList(),
)

enum class NotificationStates(val state: Int){
    ALL(1),
    ORDER(2),
    DELIVERY(3)
}

data class Notification(
    val notificationId: Long,
    val userId: Long,
    val orderId: Long,
    val title: String,
    val body: String,
    val date: String
)

fun NotificationEntity.toNotificationUiState(): Notification{
    return Notification(
        notificationId = notificationId,
        userId = userId,
        orderId = orderId,
        title = title,
        body = body,
        date = date
    )
}

fun NotificationsUiState.all() = this.notificationState == NotificationStates.ALL
fun NotificationsUiState.order() = this.notificationState == NotificationStates.ORDER
fun NotificationsUiState.delivery() = this.notificationState == NotificationStates.DELIVERY

fun NotificationsUiState.emptyNotificationsPlaceHolder() =
    this.updatedNotifications.isEmpty() && !this.isError && !this.isLoading