package org.the_chance.honeymart.ui.feature.notifications

import org.the_chance.honeymart.domain.model.NotificationEntity
import org.the_chance.honeymart.domain.util.ErrorHandler

data class NotificationsUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: ErrorHandler? = null,
    val notificationState: NotificationStates = NotificationStates.ALL,
    val notifications: List<Notification> = emptyList()
)

enum class NotificationStates(val state: Int){
    ALL(1),
    ORDER(2),
    DELIVERY(3)
}

data class Notification(
    val date: String,
    val message: String,
)

fun NotificationEntity.toNotificationUiState(): Notification{
    return Notification(
        date, ""
    )
}

fun NotificationsUiState.all() = this.notificationState == NotificationStates.ALL
fun NotificationsUiState.order() = this.notificationState == NotificationStates.ORDER
fun NotificationsUiState.delivery() = this.notificationState == NotificationStates.DELIVERY

fun NotificationsUiState.emptyNotificationsPlaceHolder() =
    this.notifications.isEmpty() && !this.isError && !this.isLoading

fun NotificationsUiState.screenContent() = this.notifications.isNotEmpty() && !this.isError