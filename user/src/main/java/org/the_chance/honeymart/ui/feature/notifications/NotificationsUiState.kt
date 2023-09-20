package org.the_chance.honeymart.ui.feature.notifications

import android.annotation.SuppressLint
import org.the_chance.honeymart.domain.model.Notification
import org.the_chance.honeymart.domain.util.ErrorHandler
import java.text.SimpleDateFormat
import java.util.Date

data class NotificationsUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: ErrorHandler? = null,
    val notificationState: NotificationStates = NotificationStates.ALL,
    val updatedNotifications: List<NotificationUiState> = emptyList(),
    val notifications: List<NotificationUiState> = emptyList(),
)

enum class NotificationStates(val state: Int){
    ALL(1),
    PROCESSING(2),
    COMPLETED(3)
}

data class NotificationUiState(
    val notificationId: Long,
    val userId: Long,
    val orderId: Long,
    val title: String,
    val body: String,
    val date: String
)

fun Notification.toNotificationUiState(): NotificationUiState{
    return NotificationUiState(
        notificationId = notificationId,
        userId = userId,
        orderId = orderId,
        title = title,
        body = body,
        date = date.toNotificationDateFormat()
    )
}
fun NotificationUiState.columnIcon() = this.title == "Order in progress!"

fun NotificationsUiState.all() = this.notificationState == NotificationStates.ALL
fun NotificationsUiState.processing() = this.notificationState == NotificationStates.PROCESSING
fun NotificationsUiState.completed() = this.notificationState == NotificationStates.COMPLETED

fun NotificationsUiState.emptyNotificationsPlaceHolder() =
    this.updatedNotifications.isEmpty() && !this.isError && !this.isLoading

@SuppressLint("SimpleDateFormat")
fun Date.toNotificationDateFormat(): String {
    val dateFormat = SimpleDateFormat("dd MMMM yyyy   HH:mm")
    return dateFormat.format(this)
}