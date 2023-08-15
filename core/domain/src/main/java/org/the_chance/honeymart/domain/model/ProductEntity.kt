package org.the_chance.honeymart.domain.model

data class ProductEntity(
    val productId: Long,
    val productName: String,
    val productDescription: String,
    val ProductPrice: Double,
    val productImages: List<String>
)