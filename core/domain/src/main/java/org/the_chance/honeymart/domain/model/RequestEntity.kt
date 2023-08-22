package org.the_chance.honeymart.domain.model

data class RequestEntity(
    val marketId: Int,
    val marketName: String,
    val imageUrl: String,
    val description: String,
    val address: String,
    val owner: OwnerProfileEntity
)
