package org.the_chance.honeymart.data.source.remote.models

import com.google.gson.annotations.SerializedName

data class OrderDto(
    @SerializedName("orderId")
    val orderId: Long? = null,
    @SerializedName("totalPrice")
    val totalPrice: Double? = null,
    @SerializedName("state")
    val state: Int? = null,
    @SerializedName("date")
    val date: Long? = null,
    @SerializedName("market")
    val market: List<MarketDto>
)
