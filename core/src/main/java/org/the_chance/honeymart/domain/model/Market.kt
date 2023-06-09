package org.the_chance.honeymart.domain.model

import org.the_chance.honeymart.data.source.remote.models.MarketDto

data class Market(
    val marketId: Long,
    val marketName: String,
)

fun MarketDto.asMarket() = Market(
    marketId = marketId,
    marketName = marketName,
)