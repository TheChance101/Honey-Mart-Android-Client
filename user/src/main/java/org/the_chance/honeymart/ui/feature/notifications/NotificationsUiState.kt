package org.the_chance.honeymart.ui.feature.notifications

import org.the_chance.honeymart.domain.model.Notification
import org.the_chance.honeymart.domain.util.ErrorHandler
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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

fun Notification.toNotificationUiState(): Notification{
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

fun convertDate(notification: Notification): String {
    val date = notification.date
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    val outputFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy   HH:mm")

    val localDateTime = LocalDateTime.parse(date, inputFormatter)
    return localDateTime.format(outputFormatter)
}