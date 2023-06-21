package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.WishListDto
import org.the_chance.honeymart.domain.model.WishListEntity

fun WishListDto.toWishListEntity() = WishListEntity(
    productId = productId,
    name = name,
    price = price
)
