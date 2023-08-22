package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.RequestDto
import org.the_chance.honeymart.domain.model.OwnerProfileEntity
import org.the_chance.honeymart.domain.model.RequestEntity

fun RequestDto.toRequestEntity(): RequestEntity {
    return RequestEntity(
        marketId = marketId ?: 0,
        marketName = marketName ?: "",
        imageUrl = imageUrl ?: "",
        description = description ?: "",
        address = address ?: "",
        owner = owner?.toOwnerProfileEntity(imageUrl ?: "")
            ?: OwnerProfileEntity(0L, "", "", "")
    )
}