package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.MarketDetailsDto
import org.the_chance.honeymart.domain.model.MarketDetails

fun MarketDetailsDto.toMarketDetails() = MarketDetails(
    marketId = marketId ?: 0L,
    marketName = marketName ?: "",
    imageUrl = imageUrl ?: "",
    productsCount = productsCount ?: 0,
    categoriesCount = categoriesCount ?: 0,
    description = description ?: "",
    address = address ?: "",
    latitude = latitude ?: 0L,
    longitude = longitude ?: 0L,
    categories = categories?.map { it.toCategory() } ?: emptyList(),
)