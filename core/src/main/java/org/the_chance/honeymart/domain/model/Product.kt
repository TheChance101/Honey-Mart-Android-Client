package org.the_chance.honeymart.domain.model

import org.the_chance.honeymart.data.source.remote.models.ProductDto

/**
 * Created by Aziza Helmy on 6/4/2023.
 */

data class Product(
    val productId: Long?,
    val productName: String?,
    val productQuantity: String?,
    val ProductPrice: Double?,
)

fun ProductDto.asProduct() = Product(
    productId = id,
    productName = name,
    productQuantity = quantity,
    ProductPrice = price
)