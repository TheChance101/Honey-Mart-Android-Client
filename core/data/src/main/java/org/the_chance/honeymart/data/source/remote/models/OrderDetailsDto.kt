package org.the_chance.honeymart.data.source.remote.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class OrderDetailsDto(
    @SerializedName("orderId")
    val orderId: Long? = null,
    @SerializedName("userId")
    val userId: Long? = null,
    @SerializedName("marketId")
    val marketId: Long? = null,
    @SerializedName("products")
    val products: List<OrderProductDto>? = null,
    @SerializedName("totalPrice")
    val totalPrice: Double? = null,
    @SerializedName("date")
    val date: String? = null,
    @SerializedName("state")
    val state: Int? = null,
)

data class OrderProductDto(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("count")
    val count: Int? = null,
    @SerializedName("price")
    val price: Double? = null,
    @SerializedName("images")
    val images: List<String>? = null
)
