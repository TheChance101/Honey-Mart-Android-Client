package org.the_chance.honeymart.data.source.remote.models

import com.google.gson.annotations.SerializedName

data class OrderDto1(
    @SerializedName("orderId")
    val orderId: Int,
    @SerializedName("totalPrice")
    val totalPrice: Double,
    @SerializedName("state")
    val state: Int,
    @SerializedName("date")
    val date: Long
)
data class OrderDto(
    val orderId: Int,
    val marketName: String,
    val orderQuantity: String,
    val orderPrice: Double
)