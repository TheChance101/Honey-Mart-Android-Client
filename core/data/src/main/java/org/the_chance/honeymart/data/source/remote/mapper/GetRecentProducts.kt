package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.GetRecentProductDto
import org.the_chance.honeymart.domain.model.GetRecentProductsEntity

fun GetRecentProductDto.toGetRecentProductEntity() = GetRecentProductsEntity(
    productId = id ?: 0L,
    productName =  name ?: "",
    productDescription = description ?: "",
    ProductPrice = price ?: 0.0,
    productImages = images ?: emptyList()
)