package org.the_chance.honeymart.data.source.remote.models

import com.google.gson.annotations.SerializedName

data class MarketOrderDto(
    @SerializedName("orderId")
    val orderId: Long? = null,
    @SerializedName("totalPrice")
    val totalPrice: Double? = null,
    @SerializedName("state")
    val state: Int? = null,
    @SerializedName("date")
    val date: Long? = null,
    @SerializedName("user")
    val user: UserDto
)

data class UserDto(
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("fullName")
    val fullName: String,
)

