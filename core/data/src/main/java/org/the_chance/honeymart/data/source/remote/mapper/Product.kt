package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.ProductDto
import org.the_chance.honeymart.domain.model.ProductEntity

fun ProductDto.toProductEntity() = ProductEntity(
    productId = id ?: 0L,
    productName =  name ?: "",
    productDescription = description ?: "",
    ProductPrice = price ?: 0.0,
    productImages = images ?: emptyList()
)