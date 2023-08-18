package org.the_chance.honeymart.domain.model


data class MarketEntity(
    val marketId: Long,
    val marketName: String,
    val imageUrl: String,
    val description: String,
    val address: String,
    val latitude: Double,
    val longitude: Double
)

