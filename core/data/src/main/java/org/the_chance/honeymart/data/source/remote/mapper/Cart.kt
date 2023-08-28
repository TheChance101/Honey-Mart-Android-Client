package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.CartDto
import org.the_chance.honeymart.data.source.remote.models.CartProductDto
import org.the_chance.honeymart.domain.model.Cart

internal fun CartDto.toCart() = Cart(
    products = products.map { it.toCartProduct() },
    total = total ?: 0.0
)

internal fun CartProductDto.toCartProduct() = Cart.Product(
    id = productId ?: 0L,
    name = name ?: "",
    count = count ?: 0,
    price = price ?: 0.0,
    images = images ?: emptyList()
)