package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.MarketRequestDto
import org.the_chance.honeymart.domain.model.MarketRequest

fun MarketRequestDto.toMarketRequest(): MarketRequest {
    return MarketRequest(
        marketId = marketId ?: 0,
        marketName = marketName ?: "",
        imageUrl = imageUrl ?: "",
        marketDescription = description ?: "",
        marketAddress = address ?: "",
        isApproved = isApproved ?: false,
        ownerName = ownerName ?: "",
        ownerEmail = ownerEmail ?: ""
    )
}
