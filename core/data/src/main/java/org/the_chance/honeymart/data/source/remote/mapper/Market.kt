package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.MarketDto
import org.the_chance.honeymart.domain.model.MarketEntity

/**
 * Created by Aziza Helmy on 6/12/2023.
 */
fun MarketDto.toMarketEntity() = MarketEntity(
    marketId = marketId ?: 0L,
    marketName = marketName ?: "",
    imageUrl = imageUrl ?: ""
)