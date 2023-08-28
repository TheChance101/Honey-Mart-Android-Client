package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.OrderDto
import org.the_chance.honeymart.data.source.remote.util.convertTimestampToDate
import org.the_chance.honeymart.domain.model.Order
import java.util.Date

fun OrderDto.toOrder(): Order {
    return Order(
        orderId = orderId ?: 0L,
        totalPrice = totalPrice ?: 0.0,
        state = state ?: 0,
        date = date?.convertTimestampToDate() ?: Date(),
        market = market.toMarket(),
        numItems = numItems ?: 0,
    )
}