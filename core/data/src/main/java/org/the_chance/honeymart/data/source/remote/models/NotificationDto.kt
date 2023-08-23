package org.the_chance.honeymart.data.source.remote.models

import com.google.gson.annotations.SerializedName

data class NotificationDto(
    @SerializedName("notificationId")
    val notificationId: Long? = null,
    @SerializedName("userId")
    val userId: Long? = null,
    @SerializedName("orderId")
    val orderId: Long? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("body")
    val body: String? = null,
    @SerializedName("date")
    val date: String? = null
)
