package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.MarketDto
import org.the_chance.honeymart.domain.model.MarketEntity


fun MarketDto.toMarketEntity() = MarketEntity(
    marketId = marketId ?: 0L,
    marketName = marketName ?: "",
    imageUrl = imageUrl ?: "",
    description = description ?: "",
    address = address ?: "",
    latitude = latitude ?: 0.0,
    longitude = longitude ?: 0.0,
)

