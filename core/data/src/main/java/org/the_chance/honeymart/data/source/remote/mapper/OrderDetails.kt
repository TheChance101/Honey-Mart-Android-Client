package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.OrderDetailsDto
import org.the_chance.honeymart.data.source.remote.models.OrderProductDto
import org.the_chance.honeymart.domain.model.OrderDetails

fun OrderDetailsDto.toOrderDetails() = OrderDetails(
    products = products?.map { it.toOrderProductDetails() } ?: emptyList(),
    totalPrice = totalPrice ?: 0.0,
    date = date ?: "",
    state = state ?: 0,
    orderId = 0L,
)

fun OrderProductDto.toOrderProductDetails() = OrderDetails.ProductDetails(
    id = id ?: 0,
    name = name ?: "",
    count = count ?: 0,
    price = price ?: 0.0,
    images = images ?: emptyList()
)