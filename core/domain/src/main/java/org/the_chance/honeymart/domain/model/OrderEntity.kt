package org.the_chance.honeymart.domain.model

data class OrderEntity (
    val orderId: Int,
    val marketName: String,
    val orderQuantity: String,
    val orderPrice: Double,
)
