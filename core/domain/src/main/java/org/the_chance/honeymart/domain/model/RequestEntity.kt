package org.the_chance.honeymart.domain.model

data class RequestEntity(
    val marketId: Int,
    val marketName: String,
    val imageUrl: String,
    val marketDescription: String,
    val marketAddress: String,
    val ownerName: String,
    val ownerEmail: String
)
