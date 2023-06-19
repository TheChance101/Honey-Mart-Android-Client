package org.the_chance.honeymart.data.source.remote.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class OrderDetailsDto(
    @SerializedName("orderId")
    val orderId: Long? = null,
    @SerializedName("userId")
    val userId: String? = null,
    @SerializedName("marketId")
    val marketId: Double? = null,
    @SerializedName("products")
    val products: List<OrderProductDto>? = null,
    @SerializedName("totalPrice")
    val totalPrice: Double? = null,
    @SerializedName("date")
    val date: Date? = null,
    @SerializedName("state")
    val state: Long? = null,
)