package org.the_chance.honeymart.domain.model

import org.the_chance.honeymart.data.source.remote.models.ProductDto

/**
 * Created by Aziza Helmy on 6/4/2023.
 */

data class Product(
    val id: Long = 0L,
    val price: Double = 0.0,
    val name: String = "",
    val quantity: String = "",
)

fun ProductDto.asProduct() = Product(
    id = id,
    name = name,
    price = price,
    quantity = quantity
    )