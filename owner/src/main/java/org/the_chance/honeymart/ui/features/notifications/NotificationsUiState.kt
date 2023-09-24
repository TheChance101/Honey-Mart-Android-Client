package org.the_chance.honeymart.ui.features.notifications

import android.annotation.SuppressLint
import org.the_chance.honeymart.domain.model.Notification
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.features.orders.OrderDetailsProductUiState
import org.the_chance.honeymart.ui.features.orders.OrderUiState
import java.text.SimpleDateFormat
import java.util.Date

data class NotificationsUiState(
    val isLoading: Boolean = false,
    val isRefresh: Boolean = false,
    val isError: Boolean = false,
    val error: ErrorHandler? = null,
    val notificationState: NotificationStates = NotificationStates.NEW,
    val products: List<OrderDetailsProductUiState> = emptyList(),
    val orderDetails: OrderUiState = OrderUiState(),
    val orders: List<OrderUiState> = emptyList(),
    val notifications: List<NotificationUiState> = emptyList(),
    val notification: NotificationUiState = NotificationUiState(),
)


enum class NotificationStates(val state: Int) {
    ALL(1),
    NEW(2),
    CANCELLED(3)
}

data class NotificationUiState(
    val notificationId: Long = 0,
    val userId: Long = 0,
    val orderId: Long = 0,
    val title: String = "",
    val body: String = "",
    val date: String = "",
    val isNotificationSelected: Boolean = false,


    )

fun List<Notification>.toNotificationUiState():List< NotificationUiState>{
    return map {NotificationUiState(
        notificationId = it.notificationId,
        userId = it.userId,
        orderId =it. orderId,
        title = it.title,
        body = it.body,
        date =it. date.toNotificationDateFormat()
    )
    }
}
fun NotificationsUiState.all() = this.notificationState == NotificationStates.ALL
fun NotificationsUiState.new() = this.notificationState == NotificationStates.NEW
fun NotificationsUiState.cancelled() = this.notificationState == NotificationStates.CANCELLED
@SuppressLint("SimpleDateFormat")
fun Date.toNotificationDateFormat(): String {
    val dateFormat = SimpleDateFormat(" HH:mm ")
    return dateFormat.format(this)
}

