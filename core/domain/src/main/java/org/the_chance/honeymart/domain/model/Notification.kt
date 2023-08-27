package org.the_chance.honeymart.domain.model

import java.util.Date

data class Notification(
    val notificationId: Long,
    val userId: Long,
    val orderId: Long,
    val title: String,
    val body: String,
    val date: Date
)
