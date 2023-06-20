package org.the_chance.honeymart.domain.model

data class CartEntity(
    val products: List<CartProductEntity>,
    val total: Double?,
)

data class CartProductEntity(
    val productId: Long?,
    val name: String?,
    val price: Double?,
    val count: Int?,
)