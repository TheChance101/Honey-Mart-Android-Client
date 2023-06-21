package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.CartDto
import org.the_chance.honeymart.data.source.remote.models.CartProductsDto
import org.the_chance.honeymart.domain.model.CartEntity
import org.the_chance.honeymart.domain.model.CartProductsEntity

internal fun CartDto.toCartEntity() = CartEntity(
    products = products?.map { it.toCartProductsEntity() },
    total = total
)

internal fun CartProductsDto.toCartProductsEntity() = CartProductsEntity(
    id = id,
    name = name,
    count = count,
    price = price
)