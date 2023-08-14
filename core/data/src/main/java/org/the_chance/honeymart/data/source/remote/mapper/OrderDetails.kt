package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.OrderDetailsDto
import org.the_chance.honeymart.data.source.remote.models.OrderProductDto
import org.the_chance.honeymart.domain.model.OrderDetailsEntity
import org.the_chance.honeymart.domain.model.OrderProductDetailsEntity

fun OrderDetailsDto.toOrderDetailsEntity() = OrderDetailsEntity(
    products = products?.map { it.toOrderProductDetailsEntity() }?: emptyList(),
    totalPrice = totalPrice?: 0.0,
    date = date?: "",
    state = state?: 0,
     orderId =  0L,
)

fun OrderProductDto.toOrderProductDetailsEntity() = OrderProductDetailsEntity(
    id = id?: 0,
    name = name?: "",
    count = count?: 0,
    price = price?: 0.0,
    images = images?: emptyList()
)