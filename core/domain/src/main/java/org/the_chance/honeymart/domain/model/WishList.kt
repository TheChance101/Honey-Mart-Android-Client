package org.the_chance.honeymart.domain.model

data class WishList(
    val productId: Long = 0L,
    val name: String = "",
    val price: Double = 0.0,
    val images: List<String> = emptyList(),
    val description: String = ""
)
