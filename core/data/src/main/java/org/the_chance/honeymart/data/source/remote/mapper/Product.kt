package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.ProductDto
import org.the_chance.honeymart.domain.model.ProductEntity

/**
 * Created by Aziza Helmy on 6/12/2023.
 */
fun ProductDto.toProductEntity() = ProductEntity(
    productId = id,
    productName = name,
    productQuantity = quantity,
    ProductPrice = price
)