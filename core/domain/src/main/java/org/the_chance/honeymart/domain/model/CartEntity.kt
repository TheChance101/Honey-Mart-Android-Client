package org.the_chance.honeymart.domain.model

data class CartEntity(
    val products: List<CartProductsEntity>?,
    val total: Double?
)

data class CartProductsEntity(
    val id: Long?,
    val name: String?,
    val count: Int?,
    val price: Double?,
    val images: List<String>?,
)