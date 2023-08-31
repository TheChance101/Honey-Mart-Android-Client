package org.the_chance.honeymart.domain.model

data class MarketInfo(
    val marketId: Long,
    val marketName: String,
    val imageUrl: String,
    val marketStatus: Boolean,
    val description: String,
    val address: String
)