package org.the_chance.honeymart.domain.model

import java.util.Date


data class Market(
    val marketId: Long,
    val marketName: String,
    val imageUrl: String,
    val description: String,
    val address: String,
    val latitude: Double,
    val longitude: Double
) {
    data class Order(
        val orderId: Long,
        val totalPrice: Double,
        val state: Int,
        val date: Date,
        val user: UserFields,
    )
}

