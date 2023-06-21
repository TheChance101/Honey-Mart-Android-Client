package org.the_chance.honeymart.domain.model

data class OrderDetailsEntity(
    val products: List<OrderProductDetailsEntity>?,
    val totalPrice: Double?,
    val date: String?,
    val state: Int?
)

data class OrderProductDetailsEntity(
    val id: Long?,
    val name: String?,
    val count: Int?,
    val price: Double?
)