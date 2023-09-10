package org.the_chance.honeymart.domain.model

data class MarketRequest(
    val marketId: Int,
    val marketName: String,
    val imageUrl: String,
    val marketDescription: String,
    val marketAddress: String,
    val isApproved: Boolean,
    val ownerName: String,
    val ownerEmail: String
)
