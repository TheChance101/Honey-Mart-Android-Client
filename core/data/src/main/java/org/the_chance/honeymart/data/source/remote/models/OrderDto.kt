package org.the_chance.honeymart.data.source.remote.models

data class OrderDto(
    val orderId: Int,
    val marketName: String,
    val orderQuantity: String,
    val orderPrice: Double
)