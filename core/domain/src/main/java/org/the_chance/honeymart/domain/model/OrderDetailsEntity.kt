package org.the_chance.honeymart.domain.model

import java.util.Date

data class OrderDetailsEntity(
    val products: List<OrderProductEntity>,
    val totalPrice: Double,
    val date: Date,
    val state: Long
)