package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.OrderDetailsDto
import org.the_chance.honeymart.domain.model.OrderDetailsEntity

fun OrderDetailsDto.toOrderDetailsEntity() = OrderDetailsEntity(
    products = products,  //products[0].toOrderProductEntity() ,
    totalPrice = totalPrice,
    date = date,
    state = state
)
