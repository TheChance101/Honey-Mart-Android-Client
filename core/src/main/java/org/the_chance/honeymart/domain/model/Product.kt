package org.the_chance.honeymart.domain.model

import org.the_chance.honeymart.data.source.remote.models.ProductDto

/**
 * Created by Aziza Helmy on 6/4/2023.
 */

data class Product(
    val id: Int,
    val name: String,
    val quantity: String,
    val price: Double,
)

  fun ProductDto.asProduct() = Product(
    id = id.toInt(),
    name = name,
    quantity = quantity,
    price = price,
)