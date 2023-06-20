package org.the_chance.honeymart.domain.model

data class ProductEntity(
    val productId: Long,
    val productName: String,
    val productQuantity: String,
    val ProductPrice: Double,
    val ProductImages: List<String>
)