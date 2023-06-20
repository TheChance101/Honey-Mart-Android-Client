package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.OrderDetailsDto
import org.the_chance.honeymart.data.source.remote.models.OrderProductDto
import org.the_chance.honeymart.domain.model.OrderDetailsEntity
import org.the_chance.honeymart.domain.model.OrderProductDetailsEntity

fun OrderDetailsDto.toOrderDetailsEntity() = OrderDetailsEntity(
    products = products?.map { it.toOrderProductDetailsEntity() },
    totalPrice = totalPrice,
    date = date,
    state = state
)

fun OrderProductDto.toOrderProductDetailsEntity() = OrderProductDetailsEntity(
    id = id,
    name = name,
    count = count,
    price = price
)