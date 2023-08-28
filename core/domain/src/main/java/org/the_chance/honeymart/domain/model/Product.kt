package org.the_chance.honeymart.domain.model

data class Product(
    val productId: Long,
    val productName: String,
    val productDescription: String,
    val productPrice: Double,
    val productImages:List<String>
)
