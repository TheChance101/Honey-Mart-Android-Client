package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.OrderDto
import org.the_chance.honeymart.domain.model.OrderEntity

fun OrderDto.toOrderEntity() = OrderEntity(
    orderId = orderId,
    totalPrice = totalPrice ,
    state = state,
    date = date
)

