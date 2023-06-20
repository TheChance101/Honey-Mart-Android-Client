package org.the_chance.honeymart.domain.model

data class CartEntity(

    val productId: Long?,
    val name: String?,
    val price: Double?,
    val count: Int?,
    val total: Double?,
)