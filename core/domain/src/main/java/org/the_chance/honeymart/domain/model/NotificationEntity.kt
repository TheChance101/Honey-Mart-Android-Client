package org.the_chance.honeymart.domain.model

data class NotificationEntity(
    val notificationId: Long,
    val userId: Long,
    val orderId: Long,
    val title: String,
    val body: String,
    val date: String
)
