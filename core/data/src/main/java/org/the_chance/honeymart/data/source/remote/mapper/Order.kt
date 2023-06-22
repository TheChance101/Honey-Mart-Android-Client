package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.MarketDto
import org.the_chance.honeymart.data.source.remote.models.OrderDto
import org.the_chance.honeymart.domain.model.MarketEntity
import org.the_chance.honeymart.domain.model.OrderEntity

fun OrderDto.toOrderEntity(): OrderEntity {
    return OrderEntity(
        orderId = orderId,
        totalPrice = totalPrice,
        state = state,
        date = date,
        market = market.toMarketEntity()
    )
}

/*fun List<MarketDto>.toListMarketEntity(): List<MarketEntity> {
    return this.map {
        MarketEntity(
            marketId = it.marketId,
            marketName = it.marketName,
            imageUrl = it.imageUrl
        )
    }
}*/
fun MarketDto.toMarketEntity(): MarketEntity {
    return MarketEntity(
        marketId = marketId,
        marketName = marketName,
        imageUrl = imageUrl
    )
}