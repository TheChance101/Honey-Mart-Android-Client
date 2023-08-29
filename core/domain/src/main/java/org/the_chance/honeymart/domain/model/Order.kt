package org.the_chance.honeymart.domain.model

import java.util.Date

data class Order(
    val orderId: Long,
    val totalPrice: Double,
    val state: Int,
    val date: Date,
    val market: Market,
    val numItems: Int,
)

