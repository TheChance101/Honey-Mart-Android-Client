package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.CartDto
import org.the_chance.honeymart.data.source.remote.models.CartProductDto
import org.the_chance.honeymart.domain.model.CartEntity
import org.the_chance.honeymart.domain.model.CartProductsEntity

internal fun CartDto.toCartEntity() = CartEntity(
    products = products.map { it.toCartProductsEntity() },
    total = total!!
)

internal fun CartProductDto.toCartProductsEntity() = CartProductsEntity(
    id = productId!!,
    name = name!!,
    count = count!!,
    price = price!!,
    images =  images?.ifEmpty { listOf("") }!!
)