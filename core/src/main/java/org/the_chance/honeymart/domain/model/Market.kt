package org.the_chance.honeymart.domain.model

import org.the_chance.honeymart.data.source.remote.models.MarketDto


data class Market(
    val id: Int,
    val name: String,
)

  fun MarketDto.asMarket() = Market(
    id = marketId.toInt(),
    name = marketName
)