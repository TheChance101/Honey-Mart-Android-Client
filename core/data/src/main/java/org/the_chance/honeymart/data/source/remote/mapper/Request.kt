package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.RequestDto
import org.the_chance.honeymart.domain.model.RequestEntity

fun RequestDto.toRequestEntity(): RequestEntity {
    return RequestEntity(
        marketId = marketId ?: 0,
        marketName = marketName ?: "",
        imageUrl = imageUrl ?: "",
        marketDescription = description ?: "",
        marketAddress = address ?: "",
        ownerName = ownerName ?: "",
        ownerEmail = ownerEmail ?: ""
    )
}
