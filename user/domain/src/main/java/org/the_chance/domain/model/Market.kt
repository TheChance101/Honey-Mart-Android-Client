package org.the_chance.domain.model

import org.the_chance.data.source.remote.models.MarketDto

/**
 * Created by Asia sama on 6/3/2023.
 * sehunexo710@gmail.com
 */
data class Market(
    val id: Int,
    val name: String,
)

fun MarketDto.asMarket() = Market(
    id = marketId.toInt(),
    name = marketName
)