package org.the_chance.honeymart.domain.model

data class OrderEntity(
    val orderId: Long,
    val totalPrice: Double,
    val state: Int,
    val date: Long,
    val market: MarketEntity,
    val numItems: Int,
)

