package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.NotificationDto
import org.the_chance.honeymart.domain.model.NotificationEntity

fun NotificationDto.toNotificationEntity(): NotificationEntity {
    return NotificationEntity(
        notificationId = notificationId ?: 0L,
        userId = userId ?: 0L,
        orderId = orderId ?: 0L,
        title = title ?: "",
        body = body ?: "",
        date = date ?: ""
    )
}