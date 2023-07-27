package org.the_chance.honeymart.domain.model

data class WishListEntity(
    val productId: Long?,
    val name: String?,
    val price: Double?,
    val images: List<String>?,
    val description: String?

)
