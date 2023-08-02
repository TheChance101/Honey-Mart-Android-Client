package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.OrderDto
import org.the_chance.honeymart.domain.model.OrderEntity

fun OrderDto.toOrderEntity(): OrderEntity {
    return OrderEntity(
        orderId = orderId ?: 0L,
        totalPrice = totalPrice ?: 0.0,
        state = state ?: 0,
        date = date ?: 0L,
        market = market.toMarketEntity(),
        numItems = numItems ?: 0
    )
}