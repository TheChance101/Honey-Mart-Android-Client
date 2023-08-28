package org.the_chance.honeymart.domain.model

data class RecentProduct(
    val productId: Long,
    val productName: String,
    val productDescription: String,
    val productPrice: Double,
    val productImages: List<String>
)