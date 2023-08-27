package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.NotificationDto
import org.the_chance.honeymart.data.source.remote.util.convertTimestampToDate
import org.the_chance.honeymart.domain.model.Notification
import java.util.Date

fun NotificationDto.toNotification(): Notification {
    return Notification(
        notificationId = notificationId ?: 0L,
        userId = userId ?: 0L,
        orderId = orderId ?: 0L,
        title = title ?: "",
        body = body ?: "",
        date = date?.convertTimestampToDate() ?: Date()
    )
}