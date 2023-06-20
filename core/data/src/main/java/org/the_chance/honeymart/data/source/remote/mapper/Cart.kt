package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.CartDto
import org.the_chance.honeymart.domain.model.CartEntity

fun CartDto.toCartEntity() = CartEntity(
    productId = productId,
    name = name,
    price =price,
    count = count,
    total = total,
)