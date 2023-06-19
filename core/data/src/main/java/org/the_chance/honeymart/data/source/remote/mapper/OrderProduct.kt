package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.OrderProductDto
import org.the_chance.honeymart.domain.model.OrderProductEntity

fun OrderProductDto.toOrderProductEntity() = OrderProductEntity(
    id = id,
    name = name,
    count = count,
    price = price
)