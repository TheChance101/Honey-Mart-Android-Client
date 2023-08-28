package org.the_chance.honeymart.domain.model

data class MarketDetails(
    val marketId: Long,
    val marketName: String,
    val imageUrl: String,
    val productsCount: Int,
    val categoriesCount: Int,
    val description: String,
    val address: String,
    val latitude: Long,
    val longitude: Long,
    val categories: List<Category>,
)
