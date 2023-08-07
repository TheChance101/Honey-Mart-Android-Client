package org.the_chance.honeymart.domain.model

data class OrderEntity(
    val orderId: Long,
    val totalPrice: Double,
    val state: Int,
    val date: Long,
    val market: MarketEntity,
    val numItems: Int,
    val user: UserEntity,
)

data class UserEntity(
    val userId: Int,
    val fullName: String,
)
