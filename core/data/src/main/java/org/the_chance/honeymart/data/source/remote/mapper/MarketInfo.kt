package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.MarketInfoDto
import org.the_chance.honeymart.domain.model.MarketInfo

fun MarketInfoDto.toMarketInfo(): MarketInfo {
    return MarketInfo(
        marketId = marketId ?: 0L,
        marketName = marketName ?: "",
        imageUrl = imageUrl ?: "",
        marketStatus = marketStatus ?: false,
        description = description ?: "",
        address = address ?: ""
    )
}