package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.ProductDto
import org.the_chance.honeymart.domain.model.ProductEntity

fun ProductDto.toProductEntity() = ProductEntity(
    productId = id,
    productName = name,
    productDescription = description,
    ProductPrice = price,
    ProductImages = images
)