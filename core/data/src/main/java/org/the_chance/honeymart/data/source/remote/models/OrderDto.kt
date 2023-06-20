package org.the_chance.honeymart.data.source.remote.models

data class OrderDto(
    val orderId: Int?= null,
    val totalPrice: Double? = null,
    val state: Int?= null,
    val date: Long?= null
)