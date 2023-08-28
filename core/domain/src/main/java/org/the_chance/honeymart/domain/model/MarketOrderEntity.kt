package org.the_chance.honeymart.domain.model

data class MarketOrderEntity(
    val orderId: Long,
    val totalPrice: Double,
    val state: Int,
    val date: Long,
    val user: UserEntity,
)

data class UserEntity(
    val userId: Int,
    val fullName: String,
)