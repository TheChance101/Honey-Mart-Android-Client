package org.the_chance.honeymart.domain.model

data class Order(
    val orderId: Long,
    val totalPrice: Double,
    val state: Int,
    val date: Long,
    val market: Market,
    val numItems: Int,
)

