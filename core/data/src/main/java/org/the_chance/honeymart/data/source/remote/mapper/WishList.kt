package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.WishListDto
import org.the_chance.honeymart.domain.model.WishList

fun WishListDto.toWishList() = WishList(
    productId = productId ?: 0L,
    name = name ?: "",
    price = price ?: 0.0,
    images = images ?: emptyList(),
    description = description ?: ""
)
